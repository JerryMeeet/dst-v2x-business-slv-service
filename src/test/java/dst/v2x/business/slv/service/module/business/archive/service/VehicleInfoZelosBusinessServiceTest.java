package dst.v2x.business.slv.service.module.business.archive.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
public class VehicleInfoZelosBusinessServiceTest {
    @Autowired
    private VehicleInfoZelosBusinessService vehicleInfoZelosBusinessService;

    /**
     * 拉取九识车辆信息
     */
    @Test
    void updateZelosVehicleInfo() {
        vehicleInfoZelosBusinessService.updateZelosVehicleInfo();
    }

    /**
     * 清理九识无效车辆信息
     */
    @Test
    void clearZelosInvalidVehicleInfo() {
        vehicleInfoZelosBusinessService.clearZelosInvalidVehicleInfo();
    }
}
