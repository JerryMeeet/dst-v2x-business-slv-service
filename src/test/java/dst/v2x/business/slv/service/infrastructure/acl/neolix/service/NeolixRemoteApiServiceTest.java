package dst.v2x.business.slv.service.infrastructure.acl.neolix.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.StationInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.TaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author pengyl
 * @date 2025/7/3
 * @description 新石器无人车远程接口服务-测试
 */
@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
@Slf4j
public class NeolixRemoteApiServiceTest {
    @Autowired
    private NeolixRemoteApiService neolixRemoteApiService;

    /**
     * 获取新石器无人车的站点信息列表
     */
    @Test
    public void getStationList() {
        List<StationInfo> stationList = neolixRemoteApiService.getStationList("LHTBY2B28PY8EA008");
        log.info("stationList: {}", stationList);
    }

    /**
     * 获取新石器无人车的任务信息列表
     */
    @Test
    public void getTaskList() {
        List<TaskInfo> taskList = neolixRemoteApiService.getTaskList("LHTBY2B28PY8EA008");
        log.info("taskList: {}", taskList);
    }
}
