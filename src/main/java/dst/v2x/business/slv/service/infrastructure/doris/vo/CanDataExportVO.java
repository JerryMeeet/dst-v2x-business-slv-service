package dst.v2x.business.slv.service.infrastructure.doris.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import dst.v2x.business.slv.service.common.config.excel.CustomExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 最后上报数据-导出类
 */
@Data
public class CanDataExportVO {
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
     * 左前轮速
     */
    @CustomExcelProperty(value = "左前轮速")
    private Double whlSpdFL;

    /**
     * 右前轮速
     */
    @CustomExcelProperty(value = "右前轮速")
    private Double whlSpdFR;

    /**
     * 左后轮速
     */
    @CustomExcelProperty(value = "左后轮速")
    private Double whlSpdRL;

    /**
     * 右后轮速
     */
    @CustomExcelProperty(value = "右后轮速")
    private Double whlSpdRR;

    /**
     * 动力电池最大允许能量回馈功率
     */
    @CustomExcelProperty(value = "动力电池最大允许能量回馈功率")
    private Double bmsChgPwr;

    /**
     * 自检状态
     * 0: 自检中
     * 1: 自检成功
     * 2: 自检失败
     */
    @CustomExcelProperty(value = "自检状态")
    private String bmsSelfChkStsName;

    /**
     * 可充电储能装
     */
    @CustomExcelProperty(value = "可充电储能装")
    private Double bmsHVBatVol;

    /**
     * 可充电储能装置总电流
     */
    @CustomExcelProperty(value = "可充电储能装置总电流")
    private Double bmsHVBatCrnt;

    /**
     * SOH（电池健康度%）
     */
    @CustomExcelProperty(value = "SOH（电池健康度%）")
    private Double bmsHVDisplaySOH;

    /**
     * 电池单体最高温度值
     */
    @CustomExcelProperty(value = "电池单体最高温度值")
    private Double bmsHVBatHighestTem;

    /**
     * 电池单体最低温度值
     */
    @CustomExcelProperty(value = "电池单体最低温度值")
    private Double bmsHVBatLowestTem;

    /**
     * 0x00: 非充电状态(比如休眠放电中放电故障等)
     * 0x01: 充电中
     * 0x02 : 加热中
     * 0x03 : 边充电边加热中
     * 0x04: 充电完成(在充电完成时，检测到充电故障发充电完成)
     * 0x05: 充电故障
     */
    @CustomExcelProperty(value = "充电状态")
    private String chargeStsName;

    /**
     * 紧急下高压电请求
     * 0x00: 无请求
     * 0x01: 请求高压下电
     */
    @CustomExcelProperty(value = "紧急下高压电请求")
    private String bmsReqHvDownName;

    /**
     * 加热继电器状态（加热继电器状态0x00 : open，0x01: close ）
     */
    @CustomExcelProperty(value = "加热继电器状态")
    private String heatRelayStsName;

    /**
     * 主正接触器状态
     * 0x00 : open
     * 0x01: close
     */
    @CustomExcelProperty(value = "主正接触器状态")
    private String parentRelayStsName;

    /**
     * 预充接触器状态
     * 0x00 : open
     * 0x01: close
     */
    @CustomExcelProperty(value = "预充接触器状态")
    private String prechargeRelayStsName;

    /**
     * 接触器状态(不区分快充慢充)
     * 0x00 : open
     * 0x01: close
     */
    @CustomExcelProperty(value = "接触器状态")
    private String chargeRelayStsName;

    /**
     * 驱动电机转速
     */
    @CustomExcelProperty(value = "驱动电机转速")
    private Double mcuSpeed;

    /**
     * 驱动电机转矩
     */
    @CustomExcelProperty(value = "驱动电机转矩")
    private Double mcuTorque;

    /**
     * 驱动电机温度
     */
    @CustomExcelProperty(value = "驱动电机温度")
    private Double mcuMotortemp;

    /**
     * 车速
     */
    @CustomExcelProperty(value = "车速")
    private Double vehicleSpeed;

    /**
     * 累计里程
     */
    @CustomExcelProperty(value = "累计里程")
    private Double vehicleOdo;

    /**
     * 车辆挡位状态
     * 0：P 挡
     * 1：D 挡
     * 2：N 挡
     * 3：R 挡
     */
    @CustomExcelProperty(value = "车辆挡位状态")
    private String vehicleGearName;

    /**
     * 油门踏板开度(%)
     */
    @CustomExcelProperty(value = "油门踏板开度(%)")
    private Double acceleratorPedalStatus;

    /**
     * 制动踏板开度(%)
     */
    @CustomExcelProperty(value = "制动踏板开度(%)")
    private Double brakePedalStatus;

    /**
     * 低压蓄电池电压值
     */
    @CustomExcelProperty(value = "低压蓄电池电压值")
    private Double bmsKeyOn;

    /**
     * 车辆运行模式
     * 0表示遥控模式，优先级最二
     * 1表示AD模式，优先级第三
     * 2表示空闲模式，优先级最低（遥控器关机，或者遥控器开机按着A 键并且连续10s遥控器没有动作）
     * 3表示故障模式，优先级最高，禁止运行AD模式，只能由空闲模式进入。
     */
    @CustomExcelProperty(value = "车辆运行模式")
    private String driveModeStateName;

    /**
     * 车辆VIN码
     */
    @CustomExcelProperty(value = "车辆VIN码")
    private String vehicleVIN;

    /**
     * VCU软件版本号
     */
    @CustomExcelProperty(value = "VCU软件版本号")
    private String vehicleVcuVersion;

    /**
     * MCU 软件版本号
     */
    @CustomExcelProperty(value = "MCU 软件版本号")
    private String vehicleMcuVersion;

    /**
     * 转向角度信号
     */
    @CustomExcelProperty(value = "转向角度信号")
    private Double behicleSteeringAngle;
}