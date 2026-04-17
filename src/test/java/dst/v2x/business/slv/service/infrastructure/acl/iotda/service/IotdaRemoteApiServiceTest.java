package dst.v2x.business.slv.service.infrastructure.acl.iotda.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author pengyl
 * @date 2025/6/27
 * @description iotda远程接口服务-测试类
 */
@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
@Slf4j
public class IotdaRemoteApiServiceTest {
    @Autowired
    private IotdaRemoteApiService iotdaRemoteApiService;

    /**
     * 新增设备
     */
    @Test
    public void addDevice() {
        String deviceId = iotdaRemoteApiService.addDevice("0000002", VehicleCompanyEnum.VEHICLE_COMPANY_1);
        log.info("deviceId:{}", deviceId);
    }
}
