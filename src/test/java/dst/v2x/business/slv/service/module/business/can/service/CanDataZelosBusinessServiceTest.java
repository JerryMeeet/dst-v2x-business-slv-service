package dst.v2x.business.slv.service.module.business.can.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * @author pengyl
 * @date 2025/8/13
 * @description
 */
@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
public class CanDataZelosBusinessServiceTest {
    @Autowired
    private CanDataZelosBusinessService canDataZelosBusinessService;

    /**
     * 获取九识无人车的CAN数据
     */
    @Test
    void getZelosCanData(){
        canDataZelosBusinessService.getZelosCanData();
    }

    /**
     * 获取九识无人车的CAN数据-指定车辆
     */
    @Test
    void getZelosCanDataByVehicleNo(){
        canDataZelosBusinessService.getZelosCanData(Arrays.asList("ZL05960"));
    }
}
