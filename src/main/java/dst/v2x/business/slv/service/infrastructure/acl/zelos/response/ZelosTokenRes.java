package dst.v2x.business.slv.service.infrastructure.acl.zelos.response;

import lombok.Data;

/**
 * 请求token响应参数
 *
 * @author 江民来
 * @date 2025年02月10日 15:42
 */
@Data
public class ZelosTokenRes {
    /**
     * app 的主键
     */
    private Integer id;

    /**
     * token 值
     */
    private String token;

    /**
     * 表示 token 在多少分钟后过期
     */
    private Integer expiresAfter;
}

