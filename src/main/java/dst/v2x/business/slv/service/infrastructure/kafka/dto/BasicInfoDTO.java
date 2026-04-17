package dst.v2x.business.slv.service.infrastructure.kafka.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 基础信息
 */
@Data
public class BasicInfoDTO {
    /**
     * 数据时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime dataTime;

    /**
     * 当前车速，单位：KM/h
     */
    private Double speed;

    /**
     * 电量，单位 %
     */
    private Double electricity;
}
