package dst.v2x.business.slv.service.infrastructure.biz.archive.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import dst.v2x.business.slv.service.common.annotation.StatusDesc;
import dst.v2x.business.slv.service.common.enums.archive.TaskStatusEnum;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 任务信息分页查询出参
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Data
public class TaskInfoPageQueryVO {
    /**
     * 主键
     */
    private String id;

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
    @StatusDesc(fieldName = "taskStatus", enumClass = TaskStatusEnum.class)
    private String taskStatusName;

    /**
     * 所属公司 1.白犀牛, 2.九识, 3.新石器
     */
    private Integer belongCompany;
    @StatusDesc(fieldName = "belongCompany", enumClass = VehicleCompanyEnum.class)
    private String belongCompanyName;

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
}
