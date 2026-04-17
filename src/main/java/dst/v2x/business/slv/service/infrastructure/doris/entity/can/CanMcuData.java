package dst.v2x.business.slv.service.infrastructure.doris.entity.can;

import lombok.Data;

/**
 * Can数据-MCU 动力系统
 */
@Data
public class CanMcuData {
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
