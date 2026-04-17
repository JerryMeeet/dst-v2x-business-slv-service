package dst.v2x.business.slv.service.infrastructure.kafka.consumer;

import com.dst.steed.vds.common.util.DstJsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawData;
import dst.v2x.business.slv.service.infrastructure.kafka.dto.BasicInfoDTO;
import dst.v2x.business.slv.service.infrastructure.kafka.dto.CanInfoDTO;
import dst.v2x.business.slv.service.infrastructure.kafka.dto.OperationInfoDTO;
import dst.v2x.business.slv.service.infrastructure.kafka.dto.RawDataDTO;
import dst.v2x.business.slv.service.module.business.raw.service.RawDataBusinessService;
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
 * KafKa监听器-无人车上报数据
 *
 * @author 张朝
 */
@Component
@Slf4j
public class RawMessageListener {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 注册 JavaTimeModule 以支持 LocalDateTime 等类型
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Autowired
    private RawDataBusinessService rawDataBusinessService;


    @KafkaListener(topics = "${spring.kafka.consumer.raw.topic}",
            groupId = "${spring.kafka.consumer.consumer-group}",
            containerFactory = "kafkaListenerContainerByteBufferFactory", batch = "true")
    public void process(List<Message<String>> messages, Acknowledgment acknowledgment) {
        List<RawData> rawDatas = getRawDatas(messages);
        if(CollectionUtils.isEmpty(rawDatas)){
            //提交偏移量
            acknowledgment.acknowledge();
        }
        log.debug("接收无人车上报数据, size:{}, {}", rawDatas.size(), DstJsonUtil.toString(rawDatas));

        try {
            //批量入库
            rawDataBusinessService.insertToDB(rawDatas);

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
    private List<RawData> getRawDatas(List<Message<String>> messages){
        try {
            List<RawData> rawDatas = new ArrayList<>();
            for (Message<String> message : messages) {
                //从头中获取车辆编码
                String vehicleNo = getVehicleNo(message.getHeaders());
                if (vehicleNo == null) {
                    continue;
                }

                //从payload中获取内容
                RawData rawData = getRawData(vehicleNo, message.getPayload());
                if (rawData == null) {
                    continue;
                }
                rawDatas.add(rawData);
            }
            return rawDatas;
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
    private RawData getRawData(String vehicleNo, String payload) {
        try {
            JsonNode rootNode = objectMapper.readTree(payload);
            JsonNode contentNode = rootNode.path("notify_data").path("body").path("content");
            if (contentNode.isMissingNode()) {
                log.warn("Content node is missing in the JSON payload.");
                return null;
            }
            RawDataDTO rawDataDTO = objectMapper.treeToValue(contentNode, RawDataDTO.class);
            RawData rawData = new RawData();

            //基础信息
            BasicInfoDTO basicInfoDTO = rawDataDTO.getBasicInfo();
            rawData.setVehicleNo(vehicleNo);
            rawData.setDataTime(basicInfoDTO.getDataTime());
            rawData.setPullTime(LocalDateTime.now());
            rawData.setSpeed(String.valueOf(basicInfoDTO.getSpeed()));
            rawData.setElectricCity(String.valueOf(basicInfoDTO.getElectricity()));

            //运营信息
            OperationInfoDTO operationInfo = rawDataDTO.getOperationInfo();
            rawData.setOnlineStatus(operationInfo.getOnlineStatus());
            rawData.setTravelStatus(operationInfo.getTravelStatus());
            rawData.setDoorStatus(operationInfo.getDoorStatus());
            rawData.setControlMode(operationInfo.getControlMode());
            rawData.setLat(String.valueOf(operationInfo.getLat()));
            rawData.setLng(String.valueOf(operationInfo.getLng()));
            rawData.setOdometer(operationInfo.getTravelTotalDistan());
            rawData.setTaskDistance(operationInfo.getTaskDistance());

            //CAN信息
            CanInfoDTO canInfoDTO = rawDataDTO.getCanInfo();
            rawData.setChargeStatus(canInfoDTO.getChargeStatus());
            rawData.setChargeGunStatus(canInfoDTO.getChargeGunStatus());
            rawData.setBatteryHealth(canInfoDTO.getBatteryHealth());
            rawData.setTplf(canInfoDTO.getTplf());
            rawData.setTplb(canInfoDTO.getTplb());
            rawData.setTprf(canInfoDTO.getTprf());
            rawData.setTprb(canInfoDTO.getTprb());
            rawData.setErrorStatus(canInfoDTO.getErrorStatus());
            return rawData;
        } catch (Exception e) {
            log.error("Error parsing JSON to RawDataDTO", e);
            return null;
        }
    }
}
