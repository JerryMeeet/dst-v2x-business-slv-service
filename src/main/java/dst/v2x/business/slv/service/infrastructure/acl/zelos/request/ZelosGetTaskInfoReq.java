package dst.v2x.business.slv.service.infrastructure.acl.zelos.request;

import lombok.Data;

import java.util.List;

/**
 * 请求任务信息入参
 */
@Data
public class ZelosGetTaskInfoReq {
    /**
     * 任务编号集合
     */
    private List<String> taskNoIds;
}

