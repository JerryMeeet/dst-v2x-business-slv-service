package dst.v2x.business.slv.service.infrastructure.biz.archive.vo;

import dst.v2x.business.slv.service.common.annotation.StatusDesc;
import dst.v2x.business.slv.service.common.enums.archive.TaskStatusEnum;
import lombok.Data;

/**
 * 当前任务信息查询出参
 *
 * @author: pengyunlin
 * @date: 2025/9/11 15:51
 */
@Data
public class TaskInfoCurrentQueryVO {
    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 任务状态,1已创建 2执⾏中 3已完成 4已取消
     */
    private Integer taskStatus;
    @StatusDesc(fieldName = "taskStatus", enumClass = TaskStatusEnum.class)
    private String taskStatusName;

    /**
     * 发车位置纬度
     */
    private Double startLat;

    /**
     * 发车位置经度
     */
    private Double startLng;

    /**
     * 目标位置纬度
     */
    private Double targetLat;

    /**
     * 目标位置经度
     */
    private Double targetLng;

    /**
     * 结束时间
     */
    private String finishTime;

    /**
     * 执行时间
     */
    private String executeTime;
}
