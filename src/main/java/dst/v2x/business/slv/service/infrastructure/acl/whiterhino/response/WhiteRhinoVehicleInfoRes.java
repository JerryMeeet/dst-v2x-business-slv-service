package dst.v2x.business.slv.service.infrastructure.acl.whiterhino.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zy
 * @date: 2024/11/8 14:54
 */
@NoArgsConstructor
@Data
/**
 * BaixiniuVehicleInfoVO类用于封装车辆信息的值对象。
 */
public class WhiteRhinoVehicleInfoRes {

    /**
     * 车辆编号（唯一标识车辆的编号）
     */
    private String vehicleNo;

    /**
     * 车牌号（用于标识车辆的牌照号码）
     */
    private String licensePlate;

    /**
     * 车架号（车辆识别号，用于唯一标识车辆）
     */
    private String vehicleVin;

    /**
     * 载重（车辆的最大承载重量，通常以吨为单位）
     */
    private String carryingCapacity;

    /**
     * 电池使用年限（车辆电池的使用年限，以年为单位）
     */
    private String batteryLifeYear;

    /**
     * 车辆所属区划（表示车辆所属的行政区划，例如省市信息）
     */
    private String city;

    /**
     * 城市区划编码（对应城市的行政区划编码，用于系统识别）
     */
    private String cityCode;

    /**
     * 地址名称（车辆停放或所在的地址名称）
     */
    private String addressName;

    /**
     * 地址编号（地址的唯一编号）
     */
    private String addressNo;

    /**
     * 地址类型（用于标识地址类型，如家庭住址、办公地点等）
     */
//    private Integer addressType;

    /**
     * 车辆满载里程（车辆在满载情况下的续航里程，单位：公里）
     */
    private String enduranceMileage;

    /**
     * 空间大小（车辆内部空间的大小，例如车厢的容积）
     */
    private String interspace;

    /**
     * 车型号（车辆的型号或类别，如轿车、货车等）
     */
    private String vehicleType;

    /**
     * 车门数量（车辆的车门数量）
     */
    private Integer doorQuantity;
}
