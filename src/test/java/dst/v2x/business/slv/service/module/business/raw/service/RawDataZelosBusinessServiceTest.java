package dst.v2x.business.slv.service.module.business.raw.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author pengyl
 * @date 2025/6/10
 * @description
 */
@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
public class RawDataZelosBusinessServiceTest {
    @Autowired
    private RawDataZelosBusinessService rawDataZelosBusinessService;

    /**
     * 获取九识无人车的实时数据
     */
    @Test
    void getWhiteRhinoVehicleDataInfo(){
        rawDataZelosBusinessService.getZelosDataInfo();
    }
}
