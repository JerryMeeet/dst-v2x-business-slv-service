package dst.v2x.business.slv.service.infrastructure.acl.neolix.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author: zy
 * @date: 2025/5/19 11:43
 */
@Data
public class NeolixVehicleInfoRes {

    /**
     * 车架号
     */
    private String vin;

    /**
     * 车架号id
     */
    private String vinId;

    /**
     * 驾驶状态
     */
    @JsonProperty("driveMode")
    private Integer controlMode;

    /**
     * 档位
     */
    private String gear;

    /**
     * 车速
     */
    private String speed;

    /**
     * 电量
     */
    private String realBattery;

    /**
     * 位置信息
     */
    private Position position;

    /**
     * 是否在线
     */
    @JsonProperty("powerState")
    private Boolean onlineStatus;

    /**
     * 是否在线
     */
    @JsonProperty("cabinetStatus")
    private Integer doorStatus;

    /**
     * 是否在线
     */
    @JsonProperty("chargingStatus")
    private Integer chargeStatus;

    /**
     * 充电枪状态
     */
    @JsonProperty("chargingGunStatus")
    private Integer chargeGunStatus;

    /**
     * 电池健康度
     */
    @JsonProperty("batteryHealth")
    private Integer batteryHealth;

    /**
     * 左前轮胎压
     */
    @JsonProperty("lfPressure")
    private Float tplf;

    /**
     * 左后轮胎压
     */
    @JsonProperty("lrPressure")
    private Float tplb;

    /**
     * 右前轮胎压
     */
    @JsonProperty("rfPressure")
    private Float tprf;

    /**
     * 右后轮胎压
     */
    @JsonProperty("rrPressure")
    private Float tprb;

    /**
     * ⻋辆故障状态
     */
    @JsonProperty("faultStatus")
    private Integer errorStatus;



    @Data
    public static class Position {
        /**
         * 纬度
         */
        private String lat;

        /**
         * 经度
         */
        private String lon;
    }

}
