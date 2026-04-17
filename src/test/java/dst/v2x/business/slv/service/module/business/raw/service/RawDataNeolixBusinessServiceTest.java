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
public class RawDataNeolixBusinessServiceTest {
    @Autowired
    private RawDataNeolixBusinessService rawDataNeolixBusinessService;

    /**
     * 拉取新石器无人车实时数据
     */
    @Test
    void getNeolixVehicleDataInfo(){
        rawDataNeolixBusinessService.getNeolixVehicleDataInfo();
    }
}
