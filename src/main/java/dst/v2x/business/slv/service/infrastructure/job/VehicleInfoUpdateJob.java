package dst.v2x.business.slv.service.infrastructure.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.utils.JobUtils;
import dst.v2x.business.slv.service.module.business.archive.service.VehicleInfoIotdaBusinessService;
import dst.v2x.business.slv.service.module.business.archive.service.VehicleInfoNeolixBusinessService;
import dst.v2x.business.slv.service.module.business.archive.service.VehicleInfoWhiteRhinoBusinessService;
import dst.v2x.business.slv.service.module.business.archive.service.VehicleInfoZelosBusinessService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 每天1点拉取无人车辆档案-入库
 *
 */
@Slf4j
@Component
public class VehicleInfoUpdateJob extends IJobHandler {
    @Resource
    private VehicleInfoNeolixBusinessService vehicleInfoNeolixBusinessService;
    @Resource
    private VehicleInfoWhiteRhinoBusinessService vehicleInfoWhiteRhinoBusinessService;
    @Resource
    private VehicleInfoZelosBusinessService vehicleInfoZelosBusinessService;
    @Resource
    private VehicleInfoIotdaBusinessService vehicleInfoIotdaBusinessService;

    @XxlJob("vehicleInfoUpdateJob")
    @Override
    public void execute() {
        try {
            String param = XxlJobHelper.getJobParam();
            XxlJobHelper.log("同步无人车辆档案 Job任务开始 Time: " + new Date());

            // 更新白犀牛车辆档案
            if(JobUtils.executeSupplierJob(param, VehicleCompanyEnum.VEHICLE_COMPANY_1)) {
                vehicleInfoWhiteRhinoBusinessService.updateWhiteRhinoVehicleInfo();
            }

            // 更新九识车辆档案
            if(JobUtils.executeSupplierJob(param, VehicleCompanyEnum.VEHICLE_COMPANY_2)) {
                vehicleInfoZelosBusinessService.updateZelosVehicleInfo();
                // 清理九识无效车辆档案
                vehicleInfoZelosBusinessService.clearZelosInvalidVehicleInfo();
            }

            // 更新新石器车档案
            if(JobUtils.executeSupplierJob(param, VehicleCompanyEnum.VEHICLE_COMPANY_3)) {
                vehicleInfoNeolixBusinessService.updateNeolixVehicleInfo();
            }

            //同步档案信息至IOTDA，用来做设备认证
            vehicleInfoIotdaBusinessService.syncDeviceInfoToIotda();


            XxlJobHelper.log("同步无人车辆档案 Job任务结束 Time: " + new Date());
        } catch (Exception exce) {
            log.error("同步无人车辆档案JobB执行发生异常！", exce);
        }
    }
}
