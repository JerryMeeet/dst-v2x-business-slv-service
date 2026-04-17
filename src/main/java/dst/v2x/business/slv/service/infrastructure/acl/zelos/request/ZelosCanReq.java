package dst.v2x.business.slv.service.infrastructure.acl.zelos.request;

import lombok.Data;

import java.util.List;

/**
 * Can数据请求类
 */
@Data
public class ZelosCanReq {
    // ⻋辆编号，最多100个
    private List<String> vehicleNoIds;
}
