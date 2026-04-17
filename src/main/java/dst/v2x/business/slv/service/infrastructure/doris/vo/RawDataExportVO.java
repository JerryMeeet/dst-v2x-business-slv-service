package dst.v2x.business.slv.service.infrastructure.doris.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import dst.v2x.business.slv.service.common.annotation.StatusDesc;
import dst.v2x.business.slv.service.common.config.excel.CustomExcelProperty;
import dst.v2x.business.slv.service.common.enums.archive.*;
import dst.v2x.business.slv.service.common.enums.raw.VehicleErrorStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 上报历史数据-导出类
 */
@Data
public class RawDataExportVO {
    /**
     * 车辆编号
     */
    @CustomExcelProperty(value = "车辆编号")
    private String vehicleNo;

    /**
     * 数据时间
     */
    @CustomExcelProperty(value = "数据采集时间")
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
    @CustomExcelProperty(value = "车速（KM/h）")
    private String speed;

    /**
     * 电量
     */
    @CustomExcelProperty(value = "电量（%）")
    private String electricCity;

    /**
     * 开关机状态：0 关机，1 开机
     */
    private Integer onlineStatus;
    @CustomExcelProperty(value = "在线状态")
    @StatusDesc(fieldName = "onlineStatus", enumClass = VehicleOnlineStatusEnum.class)
    private String onlineStatusName;

    /**
     * 行驶状态：1 空闲中，2 配送中
     */
    private Integer travelStatus;
    @CustomExcelProperty(value = "行驶状态")
    @StatusDesc(fieldName = "travelStatus", enumClass = VehicleTravelStatusEnum.class)
    private String travelStatusName;

    /**
     * 车门状态：0 未全部关闭，1 已全部关闭
     */
    private Integer doorStatus;
    @CustomExcelProperty(value = "车门状态")
    @StatusDesc(fieldName = "doorStatus", enumClass = VehicleDoorStatusEnum.class)
    private String doorStatusName;

    /**
     * 驾驶状态：1 自动驾驶，2 遥控，3 停车中
     */
    private Integer controlMode;
    @CustomExcelProperty(value = "驾驶状态")
    @StatusDesc(fieldName = "controlMode", enumClass = VehicleControlModeEnum.class)
    private String controlModeName;

    /**
     * 当前车辆位置纬度
     */
    @CustomExcelProperty(value = "当前车辆位置纬度")
    private String lat;

    /**
     * 当前车辆位置经度
     */
    @CustomExcelProperty(value = "当前车辆位置经度")
    private String lng;

    /**
     * 里程表读数
     */
    @CustomExcelProperty(value = "总里程数（KM）")
    private Double odometer;

    /**
     * 车辆任务里程数，单位：KM
     */
    @CustomExcelProperty(value = "任务里程数（KM）")
    private Double taskDistance;

    /**
     * 充电状态
     */
    private Integer chargeStatus;
    @CustomExcelProperty(value = "充电状态")
    @StatusDesc(fieldName = "chargeStatus", enumClass = VehicleChargeStatusEnum.class)
    private String chargeStatusName;

    /**
     * 充电枪状态
     */
    private Integer chargeGunStatus;
    @CustomExcelProperty(value = "充电枪状态")
    @StatusDesc(fieldName = "chargeGunStatus", enumClass = VehicelChargeGunStatusEnum.class)
    private String chargeGunStatusName;

    /**
     * 电池健康状态
     */
    @CustomExcelProperty(value = "电池健康度（%）")
    private Integer batteryHealth;

    /**
     * 左前胎压
     */
    @CustomExcelProperty(value = "左前轮胎压（KPa）")
    private Integer tplf;

    /**
     * 左后胎压
     */
    @CustomExcelProperty(value = "左后轮胎压（KPa）")
    private Integer tplb;

    /**
     * 右前胎压
     */
    @CustomExcelProperty(value = "右前轮胎压（KPa）")
    private Integer tprf;

    /**
     * 右后胎压
     */
    @CustomExcelProperty(value = "右后轮胎压（KPa）")
    private Integer tprb;

    /**
     * 车辆异常状态代码
     */
    @CustomExcelProperty(value = "车辆故障码")
    private Integer vehicleErrorCode;

    /**
     * 异常详细信息，可为空
     */
    @CustomExcelProperty(value = "车辆故障描述")
    private Object vehicleErrorMsg;

    /**
     * 车辆故障状态 1 正常 2 故障 3 自我修复中
     */
    private Integer errorStatus;
    @CustomExcelProperty(value = "车辆故障状态")
    @StatusDesc(fieldName = "errorStatus", enumClass = VehicleErrorStatusEnum.class)
    private String errorStatusName;
}