package dst.v2x.business.slv.service.infrastructure.acl.whiterhino.response;

//import com.dst.collect.service.common.annotation.StatusDesc;
//import com.dst.collect.service.common.enums.VehicelChargeGunStatusEnum;
//import com.dst.collect.service.common.enums.VehicleChargeStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BaixiniuVehicelRealtimeVO类用于封装车辆的实时信息值对象。
 *
 * @author: zy
 * @date: 2024/11/8 15:05
 */
@NoArgsConstructor
@Data
public class VehicleRealtimeRes {

    /**
     * 车辆编号（唯一标识车辆的编号，必须事先上传并且在系统中已存在）
     */
    private String vehicleNo;

    /**
     * 当前车速（车辆当前的行驶速度，单位：KM/h）
     */
    private Integer speed;

    /**
     * 电量（车辆电池的剩余电量，单位：%）
     */
    private String electricity;

    /**
     * 车辆行驶总距离（车辆自启用以来累计行驶的总距离，单位：KM）
     */
    private String travelTotalDistance;

    /**
     * 在线状态（表示车辆是否在线：
     * 0 - 离线，1 - 在线）
     */
    private Integer onlineStatus;

    /**
     * 行驶状态（表示车辆的当前行驶状态：
     * 1 - 空闲中，2 - 配送中）
     */
    private Integer travelStatus;

    /**
     * 车门状态（表示车辆车门的闭合状态：
     * 0 - 未全部关闭，1 - 已全部关闭）
     */
    private Integer doorStatus;

    /**
     * 控制模式（表示车辆的控制方式：
     * 1 - 自动驾驶，2 - 遥控，3 - 停车中）
     */
    private Integer controlMode;

    /**
     * 当前车辆位置纬度（车辆当前的位置的纬度，坐标系：WGS84）
     */
    private String lat;

    /**
     * 当前车辆位置经度（车辆当前的位置的经度，坐标系：WGS84）
     */
    private String lng;

    /**
     * 总里程数
     */
    private Double odometer;

    /**
     * 充电状态
     * 是否充电中：1充电中 0未充电-1网络异常不同步
     */
    private Integer chargeStatus;
//    @StatusDesc(fieldName = "chargeStatus", enumClass = VehicleChargeStatusEnum.class)
    private String chargeStatusName;

    /**
     * 充电枪状态
     * 充电枪状态：0未插 1正在插着 -1网络异常不同步
     */
    private Integer chargeGunStatus;
//    @StatusDesc(fieldName = "chargeGunStatus", enumClass = VehicelChargeGunStatusEnum.class)
    private String chargeGunStatusName;

    /**
     * 电池健康度
     */
    private Integer batteryHealth;

    /**
     * 左前轮胎压
     */
    private Integer tplf;

    /**
     * 左后轮胎压
     */
    private Integer tplb;

    /**
     * 右前轮胎压
     */
    private Integer tprf;

    /**
     * 右后轮胎压
     */
    private Integer tprb;

    /**
     * 车辆异常状态（表示车辆是否存在异常，若有异常则为异常代码）
     */
    private Integer vehicleErrorCode;

    /**
     * 异常详细信息（当车辆存在异常时，提供的异常详细信息）
     */
    private String vehicleErrorMsg;
    /**
     * 拉取时间
     */
    private String pullTime;
}

