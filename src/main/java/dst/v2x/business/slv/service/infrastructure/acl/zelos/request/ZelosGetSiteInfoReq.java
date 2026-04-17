package dst.v2x.business.slv.service.infrastructure.acl.zelos.request;

import lombok.Data;

import java.util.List;

/**
 * 请求站点列表入参
 */
@Data
public class ZelosGetSiteInfoReq {
    /**
     * 站点编号集合
     */
    private List<String> siteNos;
}

