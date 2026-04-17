package dst.v2x.business.slv.service.infrastructure.acl.neolix.response;

import lombok.Data;

/**
 * @author 江民来
 * @date 2025年06月12日 15:43
 */
@Data
public class VehicleFaultRes {
    /**
     * 报警编号
     */
    private String faultCode;

    /**
     * 告警名称
     */
    private String faultName;
}
