package dst.v2x.business.slv.service.infrastructure.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.utils.JobUtils;
import dst.v2x.business.slv.service.module.business.can.service.CanDataZelosBusinessService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 每5分钟拉取无人车的CAN数据
 *
 */
@Slf4j
@Component
public class VehicleCanDataJob extends IJobHandler {
    @Resource
    private CanDataZelosBusinessService canDataZelosBusinessService;

    @XxlJob("canDataInfoJob")
    @Override
    public void execute() {
        try {
            String param = XxlJobHelper.getJobParam();
            XxlJobHelper.log("同步无人车的CAN数据 Job任务开始 Time: " + new Date() + " param: " + param);

            //获取九识无人车的CAN数据
            if(JobUtils.executeSupplierJob(param, VehicleCompanyEnum.VEHICLE_COMPANY_2)) {
                canDataZelosBusinessService.getZelosCanData();
            }

            XxlJobHelper.log("同步无人车的CAN数据 Job任务结束 Time: " + new Date());
        } catch (Exception exce) {
            log.error("同步无人车的CAN数据执行发生异常！", exce);
        }
    }
}
