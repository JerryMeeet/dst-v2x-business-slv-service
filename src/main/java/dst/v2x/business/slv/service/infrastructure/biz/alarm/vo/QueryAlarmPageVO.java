package dst.v2x.business.slv.service.infrastructure.biz.alarm.vo;

import lombok.Data;

/**
 * @author 江民来
 * @date 2025年06月13日 18:53
 */
@Data
public class QueryAlarmPageVO {
    /**
     * 车辆编号
     */
    private String vehicleNo;
    /**
     * 报警编号
     */
    private Integer alarmCode;
    /**
     * 报警名称
     */
    private String alarmName;
    /**
     * 报警时间
     */
    private String alarmTime;
}
