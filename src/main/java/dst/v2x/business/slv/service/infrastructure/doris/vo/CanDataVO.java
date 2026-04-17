package dst.v2x.business.slv.service.infrastructure.doris.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import dst.v2x.business.slv.service.infrastructure.doris.vo.can.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * CAN数据-VO
 */
@Data
public class CanDataVO {
    /**
     * 车辆编号
     */
    private String vehicleNo;

    /**
     * 数据时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime dataTime;

    /**
     * 数据拉取时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime pullTime;

    /**
     * ABS数据
     */
    private CanAbsDataVO abs;

    /**
     * BMS数据
     */
    private CanBmsDataVO bms;

    /**
     * PDU高压配电系统
     */
    private CanPduDataVO pdu;

    /**
     * MCU 动力系统
     */
    private CanMcuDataVO mcu;

    /**
     * VCU整车控制系统
     */
    private CanVcuDataVO vcu;

    /**
     * 模块信息
     */
    private CanModuleDataVO module;

    /**
     * 转向角度信号
     */
    private CanPowerSteeringDataVO powerSteering;
}