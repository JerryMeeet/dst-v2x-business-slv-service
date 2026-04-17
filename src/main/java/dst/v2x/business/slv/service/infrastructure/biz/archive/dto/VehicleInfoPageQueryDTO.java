package dst.v2x.business.slv.service.infrastructure.biz.archive.dto;

import com.dst.steed.vds.common.domain.request.PageQuery;
import lombok.Data;

/**
 * 车辆信息分页查询入参
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 **/
@Data
public class VehicleInfoPageQueryDTO extends PageQuery {

    /**
     * 车辆编号（例如：1234）
     */
    private String vehicleNo;

    /**
     * 车架号（VIN码）
     */
    private String vehicleVin;

    /**
     * 国家发布的车牌 （有就上传，没有先空着）
     */
    private String licensePlate;

    /**
     * 所属公司 1.白犀牛, 2.九识，3.新石器
     */
    private Integer belongCompany;

    /**
     * 车型号
     */
    private String vehicleType;

    /**
     * iotda设备id为空
     */
    private Boolean iotdaDeviceIdIsNull;
}
