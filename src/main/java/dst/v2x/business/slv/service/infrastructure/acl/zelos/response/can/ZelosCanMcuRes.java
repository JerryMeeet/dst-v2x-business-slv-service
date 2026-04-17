package dst.v2x.business.slv.service.infrastructure.acl.zelos.response.can;

import lombok.Data;

/**
 * 九识Can数据返回类-MCU 动力系统
 */
@Data
public class ZelosCanMcuRes {
    /**
     * 驱动电机转速
     */
    private Double mcuSpeed;

    /**
     * 驱动电机转矩
     */
    private Double mcuTorque;

    /**
     * 驱动电机温度
     */
    private Double mcuMotortemp;
}
