package dst.v2x.business.slv.service.infrastructure.acl.neolix.response;

import lombok.Data;

/**
 * 新石器当前任务信息返回对象
 */
@Data
public class NeolixTaskInfoCurrentRes {
    /**
     * 任务ID
     */
    private Integer missionSubId;

    /**
     * VIN
     */
    private String vin;

    /**
     * 起点站点ID
     */
    private Integer startStationId;

    /**
     * 起点站点名称
     */
    private String startStationName;

    /**
     * 终点站点ID
     */
    private Integer endStationId;

    /**
     * 终点站点名称
     */
    private String endStationName;

    /**
     * 任务状态,1.执行中 2待执行 3.已完成，4.已取消
     */
    private Integer missionStatus;
}
