package dst.v2x.business.slv.service.infrastructure.biz.archive.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author pengyl
 * @date 2025/7/4
 * @description 任务途径点信息
 */
@Data
public class TaskGoalDTO {
    /**
     * 任务编号
     */
    private String goalNo;

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
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    /**
     * 预计到达时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime estimateArriveTime;

    /**
     * 实际到达时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime arriveTime;

    /**
     * 离开时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime departureTime;
}
