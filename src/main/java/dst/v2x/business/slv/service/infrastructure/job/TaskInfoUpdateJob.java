package dst.v2x.business.slv.service.infrastructure.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.utils.JobUtils;
import dst.v2x.business.slv.service.module.business.archive.service.TaskInfoNeolixBusinessService;
import dst.v2x.business.slv.service.module.business.archive.service.TaskInfoZelosBusinessService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 每小时15分拉取无人车任务-入库
 *
 */
@Slf4j
@Component
public class TaskInfoUpdateJob extends IJobHandler {
    @Resource
    private TaskInfoNeolixBusinessService taskInfoNeolixBusinessService;
    @Resource
    private TaskInfoZelosBusinessService taskInfoZelosBusinessService;

    @XxlJob("taskInfoUpdateJob")
    @Override
    public void execute() {
        try {
            String param = XxlJobHelper.getJobParam();
            XxlJobHelper.log("同步无人车任务 Job任务开始 Time: " + new Date());

            // 更新新石器任务信息
            if(JobUtils.executeSupplierJob(param, VehicleCompanyEnum.VEHICLE_COMPANY_3)) {
                taskInfoNeolixBusinessService.updateNeolixTaskInfo();
            }

            // 更新九识任务信息
            if(JobUtils.executeSupplierJob(param, VehicleCompanyEnum.VEHICLE_COMPANY_2)) {
                taskInfoZelosBusinessService.updateZelosTaskInfo();
            }
            
            XxlJobHelper.log("同步无人车任务 Job任务结束 Time: " + new Date());
        } catch (Exception exce) {
            log.error("同步无人车任务JobB执行发生异常！", exce);
        }
    }
}
