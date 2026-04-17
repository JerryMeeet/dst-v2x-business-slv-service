package dst.v2x.business.slv.service.infrastructure.acl.neolix.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 新石器任务信息返回对象
 */
@Data
public class NeolixTaskInfoRes {
    /**
     * 任务状态
     */
    private Integer showStatus;

    /**
     * 任务ID
     */
    private Integer missionId;

    /**
     * VIN
     */
    private String vin;

    /**
     * 发车纬度
     */
    @JsonProperty("departLat")
    private Double startLat;

    /**
     * 发车经度
     */
    @JsonProperty("departLng")
    private Double startLng;

    /**
     * 目标纬度
     */
    @JsonProperty("destinationLat")
    private Double targetLat;

    /**
     * 目标经度
     */
    @JsonProperty("destinationLng")
    private Double targetLng;

    /**
     * 发车时间
     */
    @JsonProperty("sendVehicleTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    /**
     * 预计到达时间
     */
    @JsonProperty("expectedArrivalTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime estimateArriveTime;
}
