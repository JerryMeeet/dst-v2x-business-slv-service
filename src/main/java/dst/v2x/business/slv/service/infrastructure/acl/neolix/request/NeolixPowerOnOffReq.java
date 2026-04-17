package dst.v2x.business.slv.service.infrastructure.acl.neolix.request;

import lombok.Data;

/**
 * 新石器开关机请求对象
 */
@Data
public class NeolixPowerOnOffReq {
    /**
     * 车架号
     */
    private String vin;

    /**
     * 操作类型 1--上电 2--下电
     */
    private Integer handleType;
}
