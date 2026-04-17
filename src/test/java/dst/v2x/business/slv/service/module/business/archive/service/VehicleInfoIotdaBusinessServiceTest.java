package dst.v2x.business.slv.service.module.business.archive.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author pengyl
 * @date 2025/6/27
 * @description 档案信息-车辆信息-Iotda相关服务
 */
@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
public class VehicleInfoIotdaBusinessServiceTest {
    @Autowired
    private VehicleInfoIotdaBusinessService vehicleInfoIotdaBusinessService;

    /**
     * 同步设备信息至iotda
     */
    @Test
    public void testSyncDeviceInfoToIotda() {
        vehicleInfoIotdaBusinessService.syncDeviceInfoToIotda();
    }
}
