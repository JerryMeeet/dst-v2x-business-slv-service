package dst.v2x.business.slv.service.infrastructure.acl.neolix.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 请求token响应参数
 */
@Data
public class NeolixTokenRes {

    /**
     * token值
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 表示 token 在多少分钟后过期
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;

    /**
     * token 类型
     */
    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("scope")
    private String scope;
}

