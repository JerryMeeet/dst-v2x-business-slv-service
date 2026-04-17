// AiAutoGenerateStart_3A7B2C8D5E1F4A90B7C2D1E5F8A3B6C
package dst.v2x.business.slv.service.module.business.archive.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
public class TaskInfoNeolixBusinessServiceTest {
    @Autowired
    private TaskInfoNeolixBusinessService taskInfoNeolixBusinessService;

    /**
     * 拉取新石器无人车的任务信息-测试
     */
    @Test
    void updateNeolixTaskInfo() {
        taskInfoNeolixBusinessService.updateNeolixTaskInfo();
    }
}
// AiAutoGenerateEnd_3A7B2C8D5E1F4A90B7C2D1E5F8A3B6C