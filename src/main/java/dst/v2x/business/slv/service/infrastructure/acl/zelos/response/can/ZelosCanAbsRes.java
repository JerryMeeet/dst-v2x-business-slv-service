package dst.v2x.business.slv.service.infrastructure.acl.zelos.response.can;

import lombok.Data;

/**
 * 九识Can数据返回类-abs
 */
@Data
public class ZelosCanAbsRes {
    /**
     * 左前轮速
     */
    private Double whlSpdFL;

    /**
     * 右前轮速
     */
    private Double whlSpdFR;

    /**
     * 左后轮速
     */
    private Double whlSpdRL;

    /**
     * 右后轮速
     */
    private Double whlSpdRR;
}
