package dst.v2x.business.slv.service.infrastructure.doris.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 上报数据
 * @TableName slv_raw_data
 */
@Data
@TableName(value ="slv_raw_data")
public class RawData {
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

    /**
     * 行驶状态：1 空闲中，2 配送中
     */
    private Integer travelStatus;

    /**
     * 车门状态：0 未全部关闭，1 已全部关闭
     */
    private Integer doorStatus;

    /**
     * 控制模式：1 自动驾驶，2 遥控驾驶，3 停车中，4远程驾驶
     */
    private Integer controlMode;

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
     * 充电状态，1充电中 0未充电 -1网络异常不同步
     */
    private Integer chargeStatus;

    /**
     * 充电枪状态, 0未插 1正在插着 -1网络异常不同步
     */
    private Integer chargeGunStatus;

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
     * 车辆故障状态 1 正常 2 故障 3 自我修复中
     */
    private Integer errorStatus;

    /**
     * 车辆任务里程
     */
    private Double taskDistance;
}