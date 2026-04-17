package dst.v2x.business.slv.service.infrastructure.acl.zelos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * 获取⻋辆实时动态信息-出参
 *
 * @author 江民来
 * @date 2025年02月10日 17:48
 */
@Data
public class GetAutoVehiclertInfoRes {
    /**
     * 车辆编号
     */
    private String vehicleNo;

    /**
     * 当前车速，单位：KM/h
     */
    private Integer speed;

    /**
     * 电量
     */
    @JsonProperty("electricity")
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
     * 车门状态,key表示第几个门 {"1":"DOOR_OPEN","2":"DOOR_CLOSE"}
     * "DOOR_NONE", "未知"
     * "DOOR_OPEN", "开启"
     * "DOOR_CLOSE", "关闭"
     * "DOOR_ERROR", "异常"
     */
    @JsonProperty("doorStatus")
    private Map<String, String> doorStatusMap;

    /**
     * 控制模式：1 自动驾驶，2 遥控驾驶
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
     * 总里程数
     */
    @JsonProperty("travelTotalDistance")
    private String odometer;

    /**
     * 车辆任务里程数
     */
    private String taskDistance;

    /**
     * 充电状态
     */
    private Integer chargeStatus;

    /**
     * 充电枪状态
     */
    private Integer chargeGunStatus;

    /**
     * 电池健康状态
     */
    private String batteryHealth;

    /**
     * 左前胎压
     */
    private String tplf;

    /**
     * 左后胎压
     */
    private String tplb;

    /**
     * 右前胎压
     */
    private String tprf;

    /**
     * 右后胎压
     */
    private String tprb;

    /**
     * 车辆故障状态：正常\故障\自我修复中
     */
    @JsonProperty("errorStatus")
    private String errorStatusName;

}
