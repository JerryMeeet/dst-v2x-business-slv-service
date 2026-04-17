package dst.v2x.business.slv.service.infrastructure.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.utils.JobUtils;
import dst.v2x.business.slv.service.module.business.archive.service.StationInfoNeolixBusinessService;
import dst.v2x.business.slv.service.module.business.archive.service.StationInfoZelosBusinessService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 每天1点10分拉取无人车站点-入库
 *
 */
@Slf4j
@Component
public class StationInfoUpdateJob extends IJobHandler {
    @Resource
    private StationInfoNeolixBusinessService stationsInfoNeolixBusinessService;
    @Resource
    private StationInfoZelosBusinessService stationInfoZelosBusinessService;

    @XxlJob("stationInfoUpdateJob")
    @Override
    public void execute() {
        try {
            String param = XxlJobHelper.getJobParam();
            XxlJobHelper.log("同步无人车站点 Job任务开始 Time: " + new Date());

            // 更新新石器站点信息
            if(JobUtils.executeSupplierJob(param, VehicleCompanyEnum.VEHICLE_COMPANY_3)) {
                stationsInfoNeolixBusinessService.updateNeolixStationInfo();
            }

            // 更新九识站点信息
            if(JobUtils.executeSupplierJob(param, VehicleCompanyEnum.VEHICLE_COMPANY_2)) {
                stationInfoZelosBusinessService.updateZelosStationInfo();
            }
            
            XxlJobHelper.log("同步无人车站点 Job任务结束 Time: " + new Date());
        } catch (Exception exce) {
            log.error("同步无人车站点JobB执行发生异常！", exce);
        }
    }
}
