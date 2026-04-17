package dst.v2x.business.slv.service.infrastructure.doris.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 实时报警
 * @TableName slv_raw_alarm_realtime
 */
@Data
@TableName(value ="slv_raw_alarm_realtime")
public class RawAlarmRealtime {
    /**
     * 车辆编号
     */
    private String vehicleNo;

    /**
     * 数据类型
     */
    private Integer dataType;

    /**
     * 数据时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime dataTime;

    /**
     * 急停时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime emergencyStopTime;

    /**
     * 解决时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime doneTime;
}