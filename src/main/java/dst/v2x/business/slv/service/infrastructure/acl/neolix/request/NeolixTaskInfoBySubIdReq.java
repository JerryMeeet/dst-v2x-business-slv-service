package dst.v2x.business.slv.service.infrastructure.acl.neolix.request;

import lombok.Data;

/**
 * 新石器任务详情请求对象
 */
@Data
public class NeolixTaskInfoBySubIdReq {
    /**
     * 任务id
     */
    private Integer missionSubId;
}
