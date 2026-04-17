package dst.v2x.business.slv.service.infrastructure.acl.zelos.request;

import lombok.Data;

import java.util.List;

/**
 *
 * @author 江民来
 * @date 2025年06月13日 15:14
 */
@Data
public class ZelosAlarmReq {
    // ⻋辆编号，最多100个
    private List<String> vehicleNoIds;
    // 1：预警信息 2：报警信息
    private Integer alarmmode;
}
