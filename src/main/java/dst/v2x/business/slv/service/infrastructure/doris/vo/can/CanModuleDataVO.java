package dst.v2x.business.slv.service.infrastructure.doris.vo.can;

import lombok.Data;

/**
 * Can数据-模块信息
 */
@Data
public class CanModuleDataVO {
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
