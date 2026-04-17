package dst.v2x.business.slv.service.infrastructure.doris.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengyl
 * @date 2025/6/10
 * @description
 */
@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
public class RawDataServiceImplTest {
    @Autowired
    private RawDataServiceImpl rawDataService;

    @Test
    void insertBatch(){
        List<RawData> rawDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RawData rawData = new RawData();
            rawData.setVehicleNo("111");
            rawData.setDataTime(LocalDateTime.now());
            rawData.setPullTime(LocalDateTime.now());
            rawData.setSpeed("111");
            rawData.setElectricCity("111");
            rawData.setOnlineStatus(1);
            rawData.setTravelStatus(1);
            rawData.setDoorStatus(1);
            rawData.setControlMode(1);
            rawData.setLat("111");
            rawData.setLng("111");
            rawData.setVehicleErrorCode(1);
            rawData.setVehicleErrorMsg("111");
            rawData.setOdometer(1.0);
            rawData.setChargeStatus(1);
            rawData.setChargeGunStatus(1);
            rawData.setBatteryHealth(1);
            rawData.setTplf(1);
            rawData.setTplb(1);
            rawData.setTprf(1);
            rawData.setTprb(1);
            rawDatas.add(rawData);
        }

        rawDataService.insertBatch(rawDatas);
    }

}
