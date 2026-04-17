package dst.v2x.business.slv.service.infrastructure.acl.zelos.request;

import lombok.Data;

/**
 * 请求车辆列表入参
 *
 * @author 江民来
 * @date 2025年02月10日 16:25
 */
@Data
public class ZelosGetVehicleListReq {
    private Integer pageNo;

    private Integer pageSize;
}

