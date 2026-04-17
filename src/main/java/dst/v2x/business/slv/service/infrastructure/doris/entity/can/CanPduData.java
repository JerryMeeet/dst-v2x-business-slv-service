package dst.v2x.business.slv.service.infrastructure.doris.entity.can;

import lombok.Data;

/**
 * Can数据-PDU高压配电系统
 */
@Data
public class CanPduData {
    /**
     * 主正接触器状态
     * 0x00 : open
     * 0x01: close
     */
    private Integer parentRelaySts;

    /**
     * 预充接触器状态
     * 0x00 : open
     * 0x01: close
     */
    private Integer prechargeRelaySts;

    /**
     * 接触器状态(不区分快充慢充)
     * 0x00 : open
     * 0x01: close
     */
    private Integer chargeRelaySts;
}
