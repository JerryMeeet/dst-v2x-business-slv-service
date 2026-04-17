package dst.v2x.business.slv.service.infrastructure.biz.archive.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 新增任务信息入参
 *
 * @author: pengyunlin
 * @date: 2025/9/11 15:51
 **/
@Data
public class TaskInfoAddDTO {
    /**
     * 车辆编号
     */
    @NotNull(message = "车辆编号不能为空")
    private String vehicleNo;

    /**
     * 起站点id
     */
    @NotNull(message = "起站点id不能为空")
    private String departureStationId;

    /**
     * 目的站点id
     */
    @NotNull(message = "目的站点id不能为空")
    private String destinationStationId;
}
