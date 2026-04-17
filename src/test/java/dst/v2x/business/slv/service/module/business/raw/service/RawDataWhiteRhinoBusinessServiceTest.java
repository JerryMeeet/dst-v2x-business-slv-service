package dst.v2x.business.slv.service.module.business.raw.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
public class RawDataWhiteRhinoBusinessServiceTest {
    @Autowired
    private RawDataWhiteRhinoBusinessService rawDataWhiteRhinoBusinessService;

    /**
     * 每10秒一次实时拉取最新的白犀牛无人车数据
     */
    @Test
    void getWhiteRhinoVehicleDataInfo(){
        rawDataWhiteRhinoBusinessService.getWhiteRhinoVehicleDataInfo();
    }
}
