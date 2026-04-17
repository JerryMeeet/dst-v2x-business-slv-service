package dst.v2x.business.slv.service.module.business.archive.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
public class StationInfoNeolixBusinessServiceTest {
    @Autowired
    private StationInfoNeolixBusinessService stationInfoNeolixBusinessService;

    /**
     * 拉取新石器无人车的站点信息
     */
    @Test
    void updateNeolixStationInfo() {
        stationInfoNeolixBusinessService.updateNeolixStationInfo();
    }
}
