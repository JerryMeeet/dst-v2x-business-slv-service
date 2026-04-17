package dst.v2x.business.slv.service.infrastructure.kafka.consumer;

import cn.hutool.core.bean.BeanUtil;
import com.dst.steed.vds.common.util.DstJsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawAlarmDay;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawAlarmHistory;
import dst.v2x.business.slv.service.infrastructure.doris.service.RawAlarmDayServiceImpl;
import dst.v2x.business.slv.service.infrastructure.doris.service.RawAlarmHistoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * KafKa监听器-无人车上报报警数据
 *
 * @author 张朝
 */
@Component
@Slf4j
public class AlarmMessageListener {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 注册 JavaTimeModule 以支持 LocalDateTime 等类型
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Autowired
    private RawAlarmDayServiceImpl rawAlarmDayService;
    @Autowired
    private RawAlarmHistoryServiceImpl rawAlarmHistoryService;


    @KafkaListener(topics = "${spring.kafka.consumer.alarm.topic}",
            groupId = "${spring.kafka.consumer.consumer-group}",
            containerFactory = "kafkaListenerContainerByteBufferFactory", batch = "true")
    public void process(List<Message<String>> messages, Acknowledgment acknowledgment) {
        List<RawAlarmHistory> alarmHistoryList = getRawAlarmHistoryList(messages);
        if(CollectionUtils.isEmpty(alarmHistoryList)){
            //提交偏移量
            acknowledgment.acknowledge();
        }
        log.debug("接收无人车上报报警数据, size:{}, {}", alarmHistoryList.size(), DstJsonUtil.toString(alarmHistoryList));

        try {
            //批量入库-日表，历史表与日表结构相同，所以直接copy
            List<RawAlarmDay> alarmDayList = BeanUtil.copyToList(alarmHistoryList, RawAlarmDay.class);
            rawAlarmDayService.insertBatch(alarmDayList);

            //批量入库-历史表
            rawAlarmHistoryService.insertBatch(alarmHistoryList);

            //提交偏移量
            acknowledgment.acknowledge();
        } catch (Exception e) {
            acknowledgment.nack(Duration.ofMillis(1000));
            log.error("数据库保存异常，异常信息：", e);
        }
    }

    /**
     * 获取多条数据
     * @param messages
     * @return
     */
    private List<RawAlarmHistory> getRawAlarmHistoryList(List<Message<String>> messages){
        try {
            List<RawAlarmHistory> alarmHistoryList = new ArrayList<>();
            for (Message<String> message : messages) {
                //从头中获取车辆编码
                String vehicleNo = getVehicleNo(message.getHeaders());
                if (vehicleNo == null) {
                    continue;
                }

                //从payload中获取内容
                RawAlarmHistory alarmHistory = getRawAlarmHistory(vehicleNo, message.getPayload());
                if (alarmHistory == null) {
                    continue;
                }
                alarmHistoryList.add(alarmHistory);
            }
            return alarmHistoryList;
        }catch (Exception e) {
            log.error("获取数据异常", e);
        }
        return null;
    }

    /**
     * 从头中获取车辆编码
     */
    private String getVehicleNo(MessageHeaders headers) {
        try {
            String key = headers.get(KafkaHeaders.RECEIVED_KEY, String.class);
            if(StringUtils.isBlank(key)){
                return null;
            }
            return key.substring(key.indexOf("_") + 1);
        }catch (Exception e) {
            log.error("从头中获取车辆编码异常", e);
        }
        return null;
    }

    /**
     * 从payload中获取内容
     */
    private RawAlarmHistory getRawAlarmHistory(String vehicleNo, String payload) {
        try {
            JsonNode rootNode = objectMapper.readTree(payload);
            JsonNode contentNode = rootNode.path("notify_data").path("body").path("content");
            if (contentNode.isMissingNode()) {
                log.warn("Content node is missing in the JSON payload.");
                return null;
            }
            RawAlarmHistory rawAlarmHistory = objectMapper.treeToValue(contentNode, RawAlarmHistory.class);
            rawAlarmHistory.setVehicleNo(vehicleNo);
            return rawAlarmHistory;
        } catch (Exception e) {
            log.error("Error parsing JSON to RawAlarmHistory", e);
            return null;
        }
    }
}
