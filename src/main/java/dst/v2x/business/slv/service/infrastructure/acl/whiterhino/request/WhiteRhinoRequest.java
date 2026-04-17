package dst.v2x.business.slv.service.infrastructure.acl.whiterhino.request;

import lombok.Data;

/**
 * 白犀牛-🪧请求
 *
 * @author: zy
 * @date: 2024/11/8 11:23
 */
@Data
public class WhiteRhinoRequest {

    /**
     * 具体请求参数
     */
    private String data;

    /**
     * 加签sign
     */
    private String sign;

    /**
     * appKey
     */
    private String appKey;

    /**
     * 时间戳
     */
    private Long timeStamp;

}
