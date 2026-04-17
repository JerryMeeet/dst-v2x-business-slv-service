package dst.v2x.business.slv.service.infrastructure.doris.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import dst.v2x.business.slv.service.common.annotation.StatusDesc;
import dst.v2x.business.slv.service.common.enums.archive.*;
import dst.v2x.business.slv.service.common.enums.raw.VehicleErrorStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 查询最后上报数据列表-出参
 */
@Data
public class RawDataVO {
    /**
     * 车辆编号
     */
    private String vehicleNo;

    /**
     * 数据时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime dataTime;

    /**
     * 数据拉取时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime pullTime;

    /**
     * 当前车速，单位：KM/h
     */
    private String speed;

    /**
     * 电量
     */
    private String electricCity;

    /**
     * 开关机状态：0 关机，1 开机
     */
    private Integer onlineStatus;
    @StatusDesc(fieldName = "onlineStatus", enumClass = VehicleOnlineStatusEnum.class)
    private String onlineStatusName;

    /**
     * 行驶状态：1 空闲中，2 配送中
     */
    private Integer travelStatus;
    @StatusDesc(fieldName = "travelStatus", enumClass = VehicleTravelStatusEnum.class)
    private String travelStatusName;

    /**
     * 车门状态：0 未全部关闭，1 已全部关闭
     */
    private Integer doorStatus;
    @StatusDesc(fieldName = "doorStatus", enumClass = VehicleDoorStatusEnum.class)
    private String doorStatusName;

    /**
     * 驾驶状态：1 自动驾驶，2 遥控，3 停车中
     */
    private Integer controlMode;
    @StatusDesc(fieldName = "controlMode", enumClass = VehicleControlModeEnum.class)
    private String controlModeName;

    /**
     * 当前车辆位置纬度
     */
    private String lat;

    /**
     * 当前车辆位置经度
     */
    private String lng;

    /**
     * 车辆异常状态代码
     */
    private Integer vehicleErrorCode;

    /**
     * 异常详细信息，可为空
     */
    private Object vehicleErrorMsg;

    /**
     * 里程表读数
     */
    private Double odometer;

    /**
     * 充电状态
     */
    private Integer chargeStatus;
    @StatusDesc(fieldName = "chargeStatus", enumClass = VehicleChargeStatusEnum.class)
    private String chargeStatusName;

    /**
     * 充电枪状态
     */
    private Integer chargeGunStatus;
    @StatusDesc(fieldName = "chargeGunStatus", enumClass = VehicelChargeGunStatusEnum.class)
    private String chargeGunStatusName;

    /**
     * 电池健康状态
     */
    private Integer batteryHealth;

    /**
     * 左前胎压
     */
    private Integer tplf;

    /**
     * 左后胎压
     */
    private Integer tplb;

    /**
     * 右前胎压
     */
    private Integer tprf;

    /**
     * 右后胎压
     */
    private Integer tprb;

    /**
     * ⻋辆故障状态 1 正常 2 故障 3 自我修复中
     */
    private Integer errorStatus;
    @StatusDesc(fieldName = "errorStatus", enumClass = VehicleErrorStatusEnum.class)
    private String errorStatusName;

    /**
     * 车辆任务⾥程
     */
    private Double taskDistance;
}