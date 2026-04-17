package dst.v2x.business.slv.service.infrastructure.biz.alarm.dto;

import com.dst.steed.vds.common.domain.request.PageQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 江民来
 * @date 2025年06月13日 18:46
 */
@Data
public class QueryAlarmPageDTO extends PageQuery {
    private String vehicleNo; // 车辆编号
    private Integer alarmType; // 告警类型

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime; // 开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime; // 结束时间


}
