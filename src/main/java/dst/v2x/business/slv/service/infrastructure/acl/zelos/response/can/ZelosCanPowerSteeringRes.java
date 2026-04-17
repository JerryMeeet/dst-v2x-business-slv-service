package dst.v2x.business.slv.service.infrastructure.acl.zelos.response.can;

import lombok.Data;

/**
 * 九识Can数据返回类-转向助力系统
 */
@Data
public class ZelosCanPowerSteeringRes {
    /**
     * 转向角度信号
     */
    private Double behicleSteeringAngle;
}
