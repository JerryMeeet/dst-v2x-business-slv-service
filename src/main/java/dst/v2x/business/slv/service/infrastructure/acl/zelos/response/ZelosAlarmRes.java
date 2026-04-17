package dst.v2x.business.slv.service.infrastructure.acl.zelos.response;

import lombok.Data;

/**
 * @author 江民来
 * @date 2025年06月13日 14:00
 */
@Data
public class ZelosAlarmRes {
    // 车辆编号
    private String vehicleNo;
    //报警时间
    private String alarmTimestamp;
    // 报警类型
    private String alarmType;
    // 急停时间
    private String emergencyStopTime;
    // 解决时间
    private String doneTime;
}
