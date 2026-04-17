package dst.v2x.business.slv.service.infrastructure.doris.vo.can;

import lombok.Data;

/**
 * Can数据-转向助力系统
 */
@Data
public class CanPowerSteeringDataVO {
    /**
     * 转向角度信号
     */
    private Double behicleSteeringAngle;
}
