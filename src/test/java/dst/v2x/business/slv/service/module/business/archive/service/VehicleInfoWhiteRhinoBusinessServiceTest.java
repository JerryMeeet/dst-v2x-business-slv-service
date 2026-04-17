package dst.v2x.business.slv.service.module.business.archive.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
public class VehicleInfoWhiteRhinoBusinessServiceTest {
    @Autowired
    private VehicleInfoWhiteRhinoBusinessService vehicleInfoWhiteRhinoBusinessService;

    /**
     * 拉取白犀牛最新的车辆入库及更新缓存
     */
    @Test
    void updateWhiteRhinoVehicleInfo() {
        // 这里测试 Spring 容器是否能够加载上下文
        vehicleInfoWhiteRhinoBusinessService.updateWhiteRhinoVehicleInfo();
    }
}
