package dst.v2x.business.slv.service.infrastructure.acl.neolix.request;

import lombok.Data;

/**
 * 新石器新增任务信息请求对象
 */
@Data
public class NeolixTaskInfoAddReq {
    /**
     * 车架号
     */
    private String vin;

    /**
     * 起站点id
     */
    private String departureStationId;

    /**
     * 目的站点id
     */
    private String destinationStationId;

    /**
     * "" --固定值 按需分配
     */
    private String business;
}
