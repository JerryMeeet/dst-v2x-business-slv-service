package dst.v2x.business.slv.service.infrastructure.doris.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.can.*;
import dst.v2x.business.slv.service.infrastructure.doris.entity.can.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * CAN数据
 * @TableName slv_can_data
 */
@Data
@TableName(value ="slv_can_data")
public class CanData {
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