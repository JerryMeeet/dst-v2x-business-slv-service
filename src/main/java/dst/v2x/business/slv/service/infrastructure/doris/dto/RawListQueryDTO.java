package dst.v2x.business.slv.service.infrastructure.doris.dto;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 查询设备上报数据(不分页)-入参
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 **/
@Data
public class RawListQueryDTO {
    /**
     * 车辆编号（唯一标识车辆的编号）
     */
    @NotBlank(message = "车辆编号不能为空")
    private String vehicleNo;

    /**
     * 开始查询时间
     */
    @NotNull(message = "开始时间不能为空")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime startDataTime;

    /**
     * 结束查询时间
     */
    @NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime endDataTime;
}
