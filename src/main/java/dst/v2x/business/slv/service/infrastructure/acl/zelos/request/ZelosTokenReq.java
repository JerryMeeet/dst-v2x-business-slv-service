package dst.v2x.business.slv.service.infrastructure.acl.zelos.request;

import lombok.Data;

/**
 * 请求token入参
 *
 * @author 江民来
 * @date 2025年02月10日 16:25
 */
@Data
public class ZelosTokenReq {

    /**
     * 应用id
     */
    private String appId;

    /**
     * 应用密钥
     */
    private String appKey;
}

