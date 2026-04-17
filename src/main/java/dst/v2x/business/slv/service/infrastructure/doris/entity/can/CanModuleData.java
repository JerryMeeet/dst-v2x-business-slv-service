package dst.v2x.business.slv.service.infrastructure.doris.entity.can;

import lombok.Data;

/**
 * Can数据-模块信息
 */
@Data
public class CanModuleData {
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
