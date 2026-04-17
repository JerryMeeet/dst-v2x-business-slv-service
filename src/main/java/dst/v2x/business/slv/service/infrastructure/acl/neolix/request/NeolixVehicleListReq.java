package dst.v2x.business.slv.service.infrastructure.acl.neolix.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author: zy
 * @date: 2025/5/19 11:42
 */
@Data
public class NeolixVehicleListReq {

    /**
     * 签名
     */
    private String signature;

    /**
     * 签名时间戳
     */
    private String timestamp;

    /**
     * 签名随机数
     */
    private String nonce;

    /**
     * access_token
     */
    @JsonProperty("access_token")
    private String accessToken;
}
