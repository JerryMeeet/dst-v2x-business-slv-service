package dst.v2x.business.slv.service.infrastructure.doris.vo.can;

import dst.v2x.business.slv.service.common.annotation.StatusDesc;
import dst.v2x.business.slv.service.common.enums.base.OpenCloseEnum;
import lombok.Data;

/**
 * Can数据-PDU高压配电系统
 */
@Data
public class CanPduDataVO {
    /**
     * 主正接触器状态
     * 0x00 : open
     * 0x01: close
     */
    private Integer parentRelaySts;
    @StatusDesc(fieldName = "parentRelaySts", enumClass = OpenCloseEnum.class)
    private String parentRelayStsName;

    /**
     * 预充接触器状态
     * 0x00 : open
     * 0x01: close
     */
    private Integer prechargeRelaySts;
    @StatusDesc(fieldName = "prechargeRelaySts", enumClass = OpenCloseEnum.class)
    private String prechargeRelayStsName;

    /**
     * 接触器状态(不区分快充慢充)
     * 0x00 : open
     * 0x01: close
     */
    private Integer chargeRelaySts;
    @StatusDesc(fieldName = "chargeRelaySts", enumClass = OpenCloseEnum.class)
    private String chargeRelayStsName;
}
