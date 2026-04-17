package dst.v2x.business.slv.service.infrastructure.biz.archive.dto;

import com.dst.steed.vds.common.domain.request.PageQuery;
import lombok.Data;

/**
 * 任务信息分页查询入参
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 **/
@Data
public class TaskInfoPageQueryDTO extends PageQuery {

    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * VIN
     */
    private String vin;

    /**
     * 任务状态，TaskStatusEnum 1：已创建，2：执行中，3：已完成，4：已取消
     */
    private Integer taskStatus;
}
