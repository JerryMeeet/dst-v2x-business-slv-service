package dst.v2x.business.slv.service.infrastructure.acl.neolix.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 请求token入参
 *
 * @author 江民来
 * @date 2025年02月10日 16:25
 */
@Data
public class NeolixTokenReq {

    /**
     * 应用id
     */
    @JsonProperty("client_id")
    private String clientId;

    /**
     * 应用密钥
     */
    @JsonProperty("client_secret")
    private String clientSecret;

    @JsonProperty("grant_type")
    private String grantType = "client_credentials";
}

