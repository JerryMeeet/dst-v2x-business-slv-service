package dst.v2x.business.slv.service.infrastructure.acl.zelos.response.can;

import lombok.Data;

/**
 * 九识Can数据返回类
 */
@Data
public class ZelosCanRes {
    /**
     * 车辆编号
     */
    private String name;

    /**
     * 数据时间
     */
    private String eventTime;

    /**
     * ABS数据
     */
    private ZelosCanAbsRes abs;

    /**
     * BMS数据
     */
    private ZelosCanBmsRes bms;

    /**
     * PDU高压配电系统
     */
    private ZelosCanPduRes pduOrHcm;

    /**
     * MCU 动力系统
     */
    private ZelosCanMcuRes mcu;

    /**
     * VCU整车控制系统
     */
    private ZelosCanVcuRes vcu;

    /**
     * 模块信息
     */
    private ZelosCanModuleRes module;

    /**
     * 转向角度信号
     */
    private ZelosCanPowerSteeringRes powerSteering;
}
