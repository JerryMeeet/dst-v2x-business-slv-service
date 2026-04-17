package dst.v2x.business.slv.service.infrastructure.biz.archive.vo;

import dst.v2x.business.slv.service.common.config.excel.CustomExcelProperty;
import lombok.Data;

/**
 * 车辆信息导出类
 */
@Data
public class VehicleInfoExportVO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 车辆编号（例如：1234）
     */
    @CustomExcelProperty(value = "车辆编号")
    private String vehicleNo;

    /**
     * 国家发布的车牌 （有就上传，没有先空着）
     */
    @CustomExcelProperty(value = "车牌号")
    private String licensePlate;

    /**
     * 车架号（VIN码）
     */
    @CustomExcelProperty(value = "车架号")
    private String vehicleVin;

    /**
     * 载重 (单位 KG)
     */
    @CustomExcelProperty(value = "载重 (kg)")
    private String carryingCapacity;

    /**
     * 电池使用年限 （单位年）
     */
    @CustomExcelProperty(value = "电池使用年限（年）")
    private String batteryLifeYear;

    /**
     * 车辆所属区划
     */
    @CustomExcelProperty(value = "所属城市")
    private String city;

    /**
     * 市区划编码
     */
    @CustomExcelProperty(value = "所属城市编号")
    private String cityCode;

    /**
     * 所属网点、门店、转运中心名称
     */
    @CustomExcelProperty(value = "所属客户地址")
    private String addressName;

    /**
     * 所属网点、门店、转运中心编号
     */
    @CustomExcelProperty(value = "所属客户地址编号")
    private String addressNo;

    /**
     * 车辆满载里程单位：km
     */
    @CustomExcelProperty(value = "车辆满载里程 (km)")
    private String enduranceMileage;

    /**
     * 空间大小
     */
    @CustomExcelProperty(value = "空间大小（m³）")
    private String interspace;

    /**
     * 所属公司 1.白犀牛, 2.九识, 3.新石器
     */
    @CustomExcelProperty(value = "车辆品牌")
    private String belongCompanyName;

    /**
     * 车型号
     */
    @CustomExcelProperty(value = "车型")
    private String vehicleType;

    /**
     * 车门数量（带锁的，可以开启的）
     */
    @CustomExcelProperty(value = "车门数量")
    private Integer doorQuantity;
}
