package dst.v2x.business.slv.service.module.business.archive.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
public class VehicleInfoNeolixBusinessServiceTest {
    @Autowired
    private VehicleInfoNeolixBusinessService vehicleInfoNeolixBusinessService;

    /**
     * 拉取新石器无人车的车辆信息
     */
    @Test
    void updateNeolixVehicleInfo() {
        vehicleInfoNeolixBusinessService.updateNeolixVehicleInfo();
    }
}
