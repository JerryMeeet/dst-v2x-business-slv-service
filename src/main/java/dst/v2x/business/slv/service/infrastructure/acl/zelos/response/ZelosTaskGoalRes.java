package dst.v2x.business.slv.service.infrastructure.acl.zelos.response;

import lombok.Data;

/**
 * 九识任务信息-途径点信息-出参
 */
@Data
public class ZelosTaskGoalRes {
    /**
     * 任务目标编号
     */
    private String goalNo;

    /**
     * 发车位置纬度坐-高德坐标
     */
    private String startLat;

    /**
     * 发车位置经度坐-高德坐标
     */
    private String startLng;

    /**
     * 目标位置纬度坐-高德坐标
     */
    private String targetLat;

    /**
     * 目标位置经度坐-高德坐标
     */
    private String targetLng;

    /**
     * 发车时间
     */
    private String startTime;

    /**
     * 预计到达时间
     */
    private String estimateArriveTime;

    /**
     * 实际到达时间
     */
    private String arriveTime;

    /**
     * 离开这个点的时间
     */
    private String departureTime;
}

