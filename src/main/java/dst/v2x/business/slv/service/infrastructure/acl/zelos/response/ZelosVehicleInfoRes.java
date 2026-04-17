package dst.v2x.business.slv.service.infrastructure.acl.zelos.response;

import lombok.Data;

/**
 * @author 江民来
 * @date 2025年02月10日 17:48
 */
@Data
public class ZelosVehicleInfoRes {

    /**
     * 车辆编号
     */
    private String vehicleNo;

    /**
     * 车牌号
     */
    private String licensePlate;

    /**
     * 车架号
     */
    private String vehicleVin;

    /**
     * 载重 (单位 KG)
     */
    private String carryingCapacity;

    /**
     * 电池使用年限
     */
    private String batteryLifeYear;

    /**
     * 所属城市
     */
    private String city;

    /**
     * 所属城市编号
     */
    private String cityCode;

    /**
     * 车辆满载里程单位：km
     */
    private String enduranceMileage;

    /**
     * 空间大小（m³）
     */
    private String interspace;

    /**
     * 车型
     */
    private String vehicleType;

    /**
     * 车门数量
     */
    private Integer doorQuantity;
}
