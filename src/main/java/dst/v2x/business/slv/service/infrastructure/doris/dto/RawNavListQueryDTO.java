package dst.v2x.business.slv.service.infrastructure.doris.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 上报导航数据查询-入参
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 **/
@Data
public class RawNavListQueryDTO {
    @NotBlank(message = "车号不能为空")
    private String vehicleNo;

    @NotNull(message = "历史时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "查询时间不能超过当前时间")
    private LocalDate time; // 时间 年-月-日
}
