package dst.v2x.business.slv.service.infrastructure.acl.zelos.response.can;

import lombok.Data;

/**
 * 九识Can数据返回类-PDU高压配电系统
 */
@Data
public class ZelosCanPduRes {
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
