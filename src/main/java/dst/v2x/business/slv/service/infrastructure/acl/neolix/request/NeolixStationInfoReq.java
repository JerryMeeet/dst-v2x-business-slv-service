package dst.v2x.business.slv.service.infrastructure.acl.neolix.request;

import lombok.Data;

/**
 * 新石器站点信息请求对象
 */
@Data
public class NeolixStationInfoReq {
    /**
     * 车架号
     */
    private String vin;
}
