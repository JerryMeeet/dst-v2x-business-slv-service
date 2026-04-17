package dst.v2x.business.slv.service.infrastructure.acl.zelos.response;

import lombok.Data;

/**
 * 九识车辆详情
 *
 * @author 江民来
 * @date 2025年02月10日 17:48
 */
@Data
public class QueryZelosCarInfoRes {

    /**
     * 车辆 id
     */
    private Integer id;

    /**
     * 车辆名称
     */
    private String name;

    /**
     * 车辆牌照
     */
    private String number;

    /**
     * 业务状态
     */
    private String businessStatus;

    /**
     * 业务状态名称,行驶状态
     */
    private String businessStatusName;

    /**
     * 电池电量
     */
    private Double batteryPower;

    /**
     * 车辆在线状态：0 离线 ,1在线
     */
    private Boolean online;

    /**
     * 车辆经度
     */
    private Double gcj02Lon;
    /**
     * 车辆纬度
     */
    private Double gcj02Lat;

    private Long dispatchId; // 车辆任务 id
}
