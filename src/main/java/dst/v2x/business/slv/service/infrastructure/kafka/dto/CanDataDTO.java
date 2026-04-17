package dst.v2x.business.slv.service.infrastructure.kafka.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dst.v2x.business.slv.service.infrastructure.doris.entity.can.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 上报CAN数据
 */
@Data
public class CanDataDTO {
    /**
     * 数据时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime dataTime;

    /**
     * ABS数据
     */
    private CanAbsData abs;

    /**
     * BMS数据
     */
    private CanBmsData bms;

    /**
     * PDU高压配电系统
     */
    private CanPduData pdu;

    /**
     * MCU 动力系统
     */
    private CanMcuData mcu;

    /**
     * VCU整车控制系统
     */
    private CanVcuData vcu;

    /**
     * 模块信息
     */
    private CanModuleData module;

    /**
     * 转向角度信号
     */
    private CanPowerSteeringData powerSteering;
}
