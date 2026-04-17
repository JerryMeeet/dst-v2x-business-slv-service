package dst.v2x.business.slv.service.infrastructure.acl.neolix.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 辆基础信息列表返回类
 * @author: zy
 * @date: 2025/5/19 11:43
 */
@Data
public class NeolixVehicleBaseListRes {

    /**
     * 车架号
     */
    @JsonProperty("vin")
    private String vehicleVin;

    /**
     * ⻋辆编号
     */
    @JsonProperty("vinId")
    private String vehicleNo;

    /**
     * ⻋牌号
     */
    @JsonProperty("vinCode")
    private String licensePlate;

    /**
     * 载重(单位KG)
     */
    @JsonProperty("carryingCapacity")
    private Double carryingCapacity;

    /**
     * 电池使用年限
     */
    @JsonProperty("batteryAge")
    private Double batteryLifeYear;

    /**
     * 所属城市
     */
    @JsonProperty("vehicleAoi")
    private String city;

    /**
     * 所属城市编号
     */
    @JsonProperty("cityCode")
    private String cityCode;

    /**
     * 所属客户地址
     */
    @JsonProperty("address")
    private String addressName;

    /**
     * 车辆满载里程单位：单位KM
     */
    @JsonProperty("enduranceMileage")
    private Float enduranceMileage;

    /**
     * 空间大小（m³）
     */
    @JsonProperty("interspace")
    private Float interspace;

    /**
     * 车型
     */
    @JsonProperty("vinType")
    private String vehicleType;

    /**
     * ⻋⻔数
     */
    @JsonProperty("cabinetNum")
    private Integer doorQuantity;

    /**
     * 货箱编码
     */
    @JsonProperty("cabinCode")
    private String cabinetCode;
}
