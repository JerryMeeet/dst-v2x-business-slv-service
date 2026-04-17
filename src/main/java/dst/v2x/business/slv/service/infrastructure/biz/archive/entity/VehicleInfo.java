package dst.v2x.business.slv.service.infrastructure.biz.archive.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import dst.v2x.business.slv.service.common.domain.base.BaseDomain;
import lombok.Data;

/**
 * 车辆信息表
 * @TableName slv_vehicle_info
 */
@Data
@TableName(value ="slv_vehicle_info")
public class VehicleInfo extends BaseDomain {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 车辆编号（例如：1234）
     */
    private String vehicleNo;

    /**
     * 国家发布的车牌 （有就上传，没有先空着）
     */
    private String licensePlate;

    /**
     * 车架号（VIN码）
     */
    private String vehicleVin;

    /**
     * 载重 (单位 KG)
     */
    private String carryingCapacity;

    /**
     * 电池使用年限 （单位年）
     */
    private String batteryLifeYear;

    /**
     * 车辆所属区划
     */
    private String city;

    /**
     * 市区划编码
     */
    private String cityCode;

    /**
     * 所属网点、门店、转运中心名称
     */
    private String addressName;

    /**
     * 所属网点、门店、转运中心编号
     */
    private String addressNo;

    /**
     * 车辆满载里程单位：km
     */
    private String enduranceMileage;

    /**
     * 空间大小（m³）
     */
    private String interspace;

    /**
     * 车型号
     */
    private String vehicleType;

    /**
     * 车门数量（带锁的，可以开启的）
     */
    private Integer doorQuantity;

    /**
     * 所属公司 1.白犀牛, 2.九识, 3.新石器
     */
    private Integer belongCompany;

    /**
     * iotda设备ID
     */
    private String iotdaDeviceId;

    /**
     * 货箱编码
     */
    private String cabinetCode;
}