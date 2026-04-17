package dst.v2x.business.slv.service.infrastructure.acl.neolix.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 新石器无人车响应基础类
 */
@Getter
@Setter
@ToString
public class NeolixResponse<T> {
    
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 响应body
     */
    private T data;
}
