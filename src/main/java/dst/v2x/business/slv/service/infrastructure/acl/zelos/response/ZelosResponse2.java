package dst.v2x.business.slv.service.infrastructure.acl.zelos.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 九识无人车响应基础类-新版
 * @author 江民来
 * @date 2025年06月17日 14:59
 */
@Getter
@Setter
@ToString
public class ZelosResponse2<T> {
    /**
     * 是否成功
     */
    private Boolean status;

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
