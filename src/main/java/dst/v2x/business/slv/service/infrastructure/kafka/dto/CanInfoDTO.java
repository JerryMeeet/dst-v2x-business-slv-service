package dst.v2x.business.slv.service.infrastructure.kafka.dto;

import lombok.Data;

/**
 * CAN信息
 */
@Data
public class CanInfoDTO {
    /**
     * 充电状态，1充电中 0未充电 -1网络异常不同步
     */
    private Integer chargeStatus;

    /**
     * 充电枪状态, 0未插 1正在插着 -1网络异常不同步
     */
    private Integer chargeGunStatus;

    /**
     * 电池健康度100 百分比数,最大100
     */
    private Integer batteryHealth;

    /**
     * 左前胎压，标准值290,正常范围200~350
     */
    private Integer tplf;

    /**
     * 左后胎压，标准值290,正常范围200~350
     */
    private Integer tplb;

    /**
     * 右前胎压，标准值290,正常范围200~350
     */
    private Integer tprf;

    /**
     * 右后胎压，标准值290,正常范围200~350
     */
    private Integer tprb;

    /**
     * 车辆故障状态 1 正常 2 故障 3 自我修复中
     */
    private Integer errorStatus;
}
