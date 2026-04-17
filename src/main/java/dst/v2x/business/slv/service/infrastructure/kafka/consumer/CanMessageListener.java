package dst.v2x.business.slv.service.infrastructure.kafka.consumer;

import com.dst.steed.vds.common.util.DstJsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dst.v2x.business.slv.service.infrastructure.doris.entity.CanData;
import dst.v2x.business.slv.service.infrastructure.kafka.dto.CanDataDTO;
import dst.v2x.business.slv.service.module.business.can.service.CanDataBusinessService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * KafKa监听器-无人车上报CAN数据
 *
 * @author 张朝
 */
@Component
@Slf4j
public class CanMessageListener {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 注册 JavaTimeModule 以支持 LocalDateTime 等类型
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Autowired
    private CanDataBusinessService canDataBusinessService;


    @KafkaListener(topics = "${spring.kafka.consumer.can.topic}",
            groupId = "${spring.kafka.consumer.consumer-group}",
            containerFactory = "kafkaListenerContainerByteBufferFactory", batch = "true")
    public void process(List<Message<String>> messages, Acknowledgment acknowledgment) {
        List<CanData> canDatas = getCanDatas(messages);
        if(CollectionUtils.isEmpty(canDatas)){
            //提交偏移量
            acknowledgment.acknowledge();
        }
        log.debug("接收无人车上报CAN数据, size:{}, {}", canDatas.size(), DstJsonUtil.toString(canDatas));

        try {
            //批量入库
            canDataBusinessService.insertToDB(canDatas);

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
    private List<CanData> getCanDatas(List<Message<String>> messages){
        try {
            List<CanData> canDatas = new ArrayList<>();
            for (Message<String> message : messages) {
                //从头中获取车辆编码
                String vehicleNo = getVehicleNo(message.getHeaders());
                if (vehicleNo == null) {
                    continue;
                }

                //从payload中获取内容
                CanData canData = getCanData(vehicleNo, message.getPayload());
                if (canData == null) {
                    continue;
                }
                canDatas.add(canData);
            }
            return canDatas;
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
    private CanData getCanData(String vehicleNo, String payload) {
        try {
            JsonNode rootNode = objectMapper.readTree(payload);
            JsonNode contentNode = rootNode.path("notify_data").path("body").path("content");
            if (contentNode.isMissingNode()) {
                log.warn("Content node is missing in the JSON payload.");
                return null;
            }
            CanDataDTO canDataDTO = objectMapper.treeToValue(contentNode, CanDataDTO.class);
            CanData canData = new CanData();
            canData.setVehicleNo(vehicleNo);
            canData.setDataTime(canDataDTO.getDataTime());
            canData.setPullTime(LocalDateTime.now());

            //abs数据
            canData.setAbs(canDataDTO.getAbs());

            //BMS数据
            canData.setBms(canDataDTO.getBms());

            //PDU高压配电系统
            canData.setPdu(canDataDTO.getPdu());

            //MCU 动力系统
            canData.setMcu(canDataDTO.getMcu());

            //VCU整车控制系统
            canData.setVcu(canDataDTO.getVcu());

            //模块信息
            canData.setModule(canDataDTO.getModule());

            //转向角度信号
            canData.setPowerSteering(canDataDTO.getPowerSteering());
            return canData;
        } catch (Exception e) {
            log.error("Error parsing JSON to CanDataDTO", e);
            return null;
        }
    }
}
