package dst.v2x.business.slv.service.infrastructure.acl.neolix.request;

import lombok.Data;

/**
 * 新石器任务信息请求对象
 */
@Data
public class NeolixTaskInfoReq {
    /**
     * 车架号
     */
    private String vin;

    /**
     * 货箱编码
     */
    private String cabinetCode;
}
