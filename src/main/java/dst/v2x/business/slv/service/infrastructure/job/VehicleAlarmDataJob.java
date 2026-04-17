package dst.v2x.business.slv.service.infrastructure.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.utils.JobUtils;
import dst.v2x.business.slv.service.module.business.raw.service.RawAlarmNeolixBusinessService;
import dst.v2x.business.slv.service.module.business.raw.service.RawAlarmZelosBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 每10秒拉取无人车的告警信息
 */
@Slf4j
@Component
public class VehicleAlarmDataJob extends IJobHandler {
    @Autowired
    private RawAlarmNeolixBusinessService alarmNeolixBusinessService;
    @Autowired
    private RawAlarmZelosBusinessService alarmZelosBusinessService;

    @XxlJob("vehicleAlarmDataJob")
    @Override
    public void execute() {
        try {
            String param = XxlJobHelper.getJobParam();
            XxlJobHelper.log("同步无人车的->报警信息 Job任务开始 Time: " + new Date());
            // 拉取新石器无人车报警数据
            if(JobUtils.executeSupplierJob(param, VehicleCompanyEnum.VEHICLE_COMPANY_3)) {
                alarmNeolixBusinessService.getNeolixVehicleAlarmDataInfo();
            }
            // 拉取九识无人车报警数据
            if(JobUtils.executeSupplierJob(param, VehicleCompanyEnum.VEHICLE_COMPANY_2)) {
                alarmZelosBusinessService.getZelosVehicleAlarmDataInfo();
            }
            XxlJobHelper.log("同步无人车的->报警信息 Job任务结束 Time: " + new Date());
        } catch (Exception exce) {
            log.error("同步无人车的报警信息执行发生异常！", exce);
            XxlJobHelper.handleFail("任务执行失败: " + exce.getMessage());
        }
    }
}
