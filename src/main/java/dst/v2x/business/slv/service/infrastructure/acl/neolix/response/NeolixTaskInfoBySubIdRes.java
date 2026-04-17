package dst.v2x.business.slv.service.infrastructure.acl.neolix.response;

import lombok.Data;

/**
 * 新石器任务详情返回对象
 */
@Data
public class NeolixTaskInfoBySubIdRes {
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
     * 剩余时间
     */
    private Integer eta;

    /**
     * 剩余距离
     */
    private Integer distance;

    /**
     * 起始站点纬度
     */
    private Double startLat;

    /**
     * 起始站点经度
     */
    private Double startLng;

    /**
     * 终点纬度
     */
    private Double endLat;

    /**
     * 终点经度
     */
    private Double endLng;

    /**
     * 任务行驶距离
     */
    private Integer drivingDistance;

    /**
     * 结束时间
     */
    private String finishTime;

    /**
     * 执行时间
     */
    private String executeTime;

    /**
     * 任务状态,1.执行中 2待执行 3.已完成，4.已取消
     */
    private Integer missionStatus;
}
