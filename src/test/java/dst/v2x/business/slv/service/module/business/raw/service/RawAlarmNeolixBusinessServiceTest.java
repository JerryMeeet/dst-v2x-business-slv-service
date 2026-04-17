package dst.v2x.business.slv.service.module.business.raw.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 江民来
 * @date 2025年06月13日 17:19
 */
@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
public class RawAlarmNeolixBusinessServiceTest {
    @Autowired
    private RawAlarmNeolixBusinessService rawAlarmNeolixBusinessService;

    /**
     * 测试新石器报警数据接口
     *
     * @author 江民来
     * @date 2025/6/18 10:41
     */
    @Test
    public void getNeolixVehicleAlarmDataInfoTest() {
        rawAlarmNeolixBusinessService.getNeolixVehicleAlarmDataInfo();
    }
}
