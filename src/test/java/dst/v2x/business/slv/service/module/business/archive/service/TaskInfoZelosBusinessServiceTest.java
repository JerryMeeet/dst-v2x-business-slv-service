package dst.v2x.business.slv.service.module.business.archive.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
public class TaskInfoZelosBusinessServiceTest {
    @Autowired
    private TaskInfoZelosBusinessService taskInfoZelosBusinessService;

    /**
     * 拉取九识无人车的任务信息-测试
     */
    @Test
    void updateZelosTaskInfo() {
        taskInfoZelosBusinessService.updateZelosTaskInfo();
    }
}
