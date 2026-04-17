package dst.v2x.business.slv.service.infrastructure.acl.zelos.response.can;

import lombok.Data;

/**
 * 九识Can数据返回类-模块信息
 */
@Data
public class ZelosCanModuleRes {
    /**
     * 车辆VIN码
     */
    private String vehicleVIN;

    /**
     * VCU软件版本号
     */
    private String vehicleVcuVersion;

    /**
     * MCU 软件版本号
     */
    private String vehicleMcuVersion;
}
