package dst.v2x.business.slv.service.infrastructure.acl.zelos.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 九识无人车响应基础类
 *
 * @author 江民来
 * @date 2025年02月10日 15:35
 */
@Getter
@Setter
@ToString
public class ZelosResponse<T> {
    
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 响应body
     */
    private T data;
}
