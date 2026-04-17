package dst.v2x.business.slv.service.infrastructure.doris.entity.can;

import lombok.Data;

/**
 * Can数据-转向助力系统
 */
@Data
public class CanPowerSteeringData {
    /**
     * 转向角度信号
     */
    private Double behicleSteeringAngle;
}
