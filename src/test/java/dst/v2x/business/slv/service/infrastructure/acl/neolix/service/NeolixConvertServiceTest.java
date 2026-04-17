// AiAutoGenerateStart_3A7B2C8D5E1F4A90B7C2D1E5F8A3B6C
package dst.v2x.business.slv.service.infrastructure.acl.neolix.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.enmus.NeolixTaskStatusEnum;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixStationInfoRes;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixTaskInfoRes;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.TaskGoalDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.StationInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.TaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
@Slf4j
public class NeolixConvertServiceTest {
    @Autowired
    private NeolixConvertService neolixConvertService;

    private NeolixTaskInfoRes taskInfoRes;
    private NeolixStationInfoRes stationInfoRes;

    @BeforeEach
    public void setUp() {
        // 初始化测试数据
        taskInfoRes = new NeolixTaskInfoRes();
        taskInfoRes.setMissionId(123);
        taskInfoRes.setVin("LHTBY2B28PY8EA008");
        taskInfoRes.setShowStatus(NeolixTaskStatusEnum.STATUS_1.getCode());
        taskInfoRes.setStartTime(LocalDateTime.ofEpochSecond(1625145678, 0, ZoneOffset.UTC));
        taskInfoRes.setEstimateArriveTime(LocalDateTime.ofEpochSecond(1625149278, 0, ZoneOffset.UTC));
        taskInfoRes.setStartLat(39.9042);
        taskInfoRes.setStartLng(116.4074);
        taskInfoRes.setTargetLat(40.9042);
        taskInfoRes.setTargetLng(117.4074);

        stationInfoRes = new NeolixStationInfoRes();
        stationInfoRes.setStationId(456);
        stationInfoRes.setStationName("Test Station");
        stationInfoRes.setStationAddress("123 Test St");
        stationInfoRes.setLat(39.9042);
        stationInfoRes.setLng(116.4074);
    }

    @Test
    public void convertTaskInfo() {
        TaskInfo result = neolixConvertService.convertTaskInfo(taskInfoRes);
        assertNotNull(result);
        assertEquals("123", result.getTaskNo());
        assertEquals("LHTBY2B28PY8EA008", result.getVin());
        assertEquals(NeolixTaskStatusEnum.STATUS_1.getCode(), result.getTaskStatus());
        assertEquals(VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode(), result.getBelongCompany());
        assertNotNull(result.getGoals());
        log.info("TaskInfo: {}", result);
    }

    @Test
    public void convertStationInfo() {
        StationInfo result = neolixConvertService.convertStationInfo(stationInfoRes);
        assertNotNull(result);
        assertEquals("456", result.getStationNo());
        assertEquals("Test Station", result.getStationName());
        assertEquals("123 Test St", result.getStationAddress());
        assertEquals(VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode(), result.getBelongCompany());
        assertEquals(39.9042, result.getLat());
        assertEquals(116.4074, result.getLng());
        log.info("StationInfo: {}", result);
    }
}
// AiAutoGenerateEnd_3A7B2C8D5E1F4A90B7C2D1E5F8A3B6C