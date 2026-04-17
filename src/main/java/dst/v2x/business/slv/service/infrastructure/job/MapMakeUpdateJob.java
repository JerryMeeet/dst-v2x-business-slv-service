package dst.v2x.business.slv.service.infrastructure.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.utils.JobUtils;
import dst.v2x.business.slv.service.module.business.archive.service.MapMakeZelosBusinessService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 1天拉取无人车路线-入库
 *
 */
@Slf4j
@Component
public class MapMakeUpdateJob extends IJobHandler {
    @Resource
    private MapMakeZelosBusinessService mapMakeZelosBusinessService;

    @XxlJob("mapMakeUpdateJob")
    @Override
    public void execute() {
        try {
            String param = XxlJobHelper.getJobParam();
            XxlJobHelper.log("同步无人车路线 Job路线开始 Time: " + new Date());

            // 更新九识路线信息
            if(JobUtils.executeSupplierJob(param, VehicleCompanyEnum.VEHICLE_COMPANY_2)) {
                mapMakeZelosBusinessService.updateZelosMapMake();
            }
            
            XxlJobHelper.log("同步无人车路线 Job路线结束 Time: " + new Date());
        } catch (Exception exce) {
            log.error("同步无人车路线JobB执行发生异常！", exce);
        }
    }
}
