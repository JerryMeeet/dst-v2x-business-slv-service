package dst.v2x.business.slv.service.infrastructure.acl.zelos.response;

import lombok.Data;

import java.util.List;

/**
 * 九识任务信息-出参
 */
@Data
public class ZelosTaskInfoRes {
    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 车辆编号
     */
    private String vehicleNo;

    /**
     * 任务状态
     */
    private String taskStatus;

    /**
     * 发车电量 单位 %
     */
    private String startElectricity;

    /**
     * 到达电量 单位 %
     */
    private String arriveElectricity;

    /**
     * 任务开始时间
     */
    private String startTime;

    /**
     * 任务结束时间
     */
    private String endTime;

    /**
     * 途径点信息集合
     */
    private List<ZelosTaskGoalRes> goals;
}