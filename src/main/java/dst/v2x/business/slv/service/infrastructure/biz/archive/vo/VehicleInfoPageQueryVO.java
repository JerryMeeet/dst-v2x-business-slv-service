package dst.v2x.business.slv.service.infrastructure.biz.archive.vo;

import lombok.Data;

import java.util.Date;

/**
 * 车辆信息分页查询出参
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Data
public class VehicleInfoPageQueryVO {
    /**
     * 主键
     */
    private String id;

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
     * 空间大小
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
     * 删除标记 0正常 1删除
     */
    private Integer isDeleted;

    /**
     * 创建人id
     */
    private Long creator;

    /**
     * 创建人
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人ID
     */
    private Long modifier;

    /**
     * 修改人
     */
    private String modifierName;

    /**
     * 修改时间
     */
    private Date modifyTime;


    /**
     * 所属公司 1.白犀牛, 2.九识, 3.新石器
     */
    private Integer belongCompany;
    private String belongCompanyName;
}
