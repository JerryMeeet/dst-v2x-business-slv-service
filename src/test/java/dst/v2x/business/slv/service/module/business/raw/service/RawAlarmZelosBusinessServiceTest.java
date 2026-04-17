package dst.v2x.business.slv.service.module.business.raw.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosAlarmRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 江民来
 * @date 2025年06月13日 17:13
 */
@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
public class RawAlarmZelosBusinessServiceTest {
    @Autowired
    private RawAlarmZelosBusinessService rawAlarmZelosBusinessService;

    /**
     * 拉去九识无人车报警数据
     *
     * @author 江民来
     * @date 2025/6/19 11:02
     */
    @Test
    public void getZelosVehicleAlarmDataInfoTest() {
        rawAlarmZelosBusinessService.getZelosVehicleAlarmDataInfo();
    }

    @Test
    public void getRealTimeDataFromZelosTest() {
        List<ZelosAlarmRes> res = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ZelosAlarmRes alarmRes = new ZelosAlarmRes();
            /*alarmRes.setVehicleNo("TEST00" + i);
            alarmRes.setAlarmTimestamp(LocalDateTime.now());
            alarmRes.setAlarmType(i + "");
            alarmRes.setEmergencyStopTime(LocalDateTime.now());
            alarmRes.setDoneTime(LocalDateTime.now());*/
            res.add(alarmRes);
        }
        rawAlarmZelosBusinessService.getRealTimeDataFromZelos(res);
    }

    @Test
    public void getRawAlarmHistoryFromZelosTest() {
        List<ZelosAlarmRes> res = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ZelosAlarmRes alarmRes = new ZelosAlarmRes();
           /* alarmRes.setVehicleNo("TEST00" + i);
            alarmRes.setAlarmTimestamp(LocalDateTime.now());
            alarmRes.setAlarmType(i + "");
            alarmRes.setEmergencyStopTime(LocalDateTime.now());
            alarmRes.setDoneTime(LocalDateTime.now());
            res.add(alarmRes);*/
        }
        rawAlarmZelosBusinessService.getRawAlarmHistoryFromZelos(res);
    }
}
