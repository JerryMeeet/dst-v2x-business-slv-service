package dst.v2x.business.slv.service.infrastructure.biz.archive.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import dst.v2x.business.slv.service.common.domain.base.BaseDomain;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 任务信息表
 * @TableName slv_task_info
 */
@Data
@TableName(value ="slv_task_info")
public class TaskInfo extends BaseDomain {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 车辆VIN
     */
    private String vin;

    /**
     * 任务状态,1已创建 2执⾏中 3已完成 4已取消
     */
    private Integer taskStatus;

    /**
     * 所属公司 1.白犀牛, 2.九识, 3.新石器
     */
    private Integer belongCompany;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    /**
     * 途径点信息数组,json字符串
     */
    private String goals;
}