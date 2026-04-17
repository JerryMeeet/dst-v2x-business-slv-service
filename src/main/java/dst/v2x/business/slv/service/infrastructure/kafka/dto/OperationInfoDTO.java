package dst.v2x.business.slv.service.infrastructure.kafka.dto;

import lombok.Data;

/**
 * 运营信息
 */
@Data
public class OperationInfoDTO {
    /**
     * 在线状态：0 离线   1在线
     */
    private Integer onlineStatus;

    /**
     * 行驶状态：1 空闲中，2 配送中
     */
    private Integer travelStatus;

    /**
     * 车门状态：0 未全部关闭，1 已全部关闭
     */
    private Integer doorStatus;

    /**
     * 控制模式：1 自动驾驶，2 遥控驾驶，3 停车中，4远程驾驶
     */
    private Integer controlMode;

    /**
     * 当前车辆位置纬度，高德火星坐标
     */
    private Double lat;

    /**
     * 当前车辆位置经度，高德火星坐标
     */
    private Double lng;

    /**
     * 车辆行驶总距离 单位 KM
     */
    private Double travelTotalDistan;

    /**
     * 车辆任务里程车辆任务里程 单位 KM
     */
    private Double taskDistance;
}
