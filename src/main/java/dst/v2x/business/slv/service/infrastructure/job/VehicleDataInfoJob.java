package dst.v2x.business.slv.service.infrastructure.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.utils.JobUtils;
import dst.v2x.business.slv.service.module.business.raw.service.RawDataNeolixBusinessService;
import dst.v2x.business.slv.service.module.business.raw.service.RawDataWhiteRhinoBusinessService;
import dst.v2x.business.slv.service.module.business.raw.service.RawDataZelosBusinessService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 每10秒拉取无人车的实时信息
 *
 */
@Slf4j
@Component
public class VehicleDataInfoJob extends IJobHandler {
    @Resource
    private RawDataNeolixBusinessService rawDataNeolixBusinessService;
    @Resource
    private RawDataWhiteRhinoBusinessService rawDataWhiteRhinoBusinessService;
    @Resource
    private RawDataZelosBusinessService rawDataZelosBusinessService;

    @XxlJob("vehicleDataInfoJob")
    @Override
    public void execute() {
        try {
            String param = XxlJobHelper.getJobParam();
            XxlJobHelper.log("同步无人车的实时信息 Job任务开始 Time: " + new Date() + " param: " + param);

            //拉取新石器无人车实时数据
            if(JobUtils.executeSupplierJob(param, VehicleCompanyEnum.VEHICLE_COMPANY_3)){
                rawDataNeolixBusinessService.getNeolixVehicleDataInfo();
            }

            //拉取最新的白犀牛无人车数据
            if(JobUtils.executeSupplierJob(param, VehicleCompanyEnum.VEHICLE_COMPANY_1)) {
                rawDataWhiteRhinoBusinessService.getWhiteRhinoVehicleDataInfo();
            }

            //获取九识无人车的实时数据
            if(JobUtils.executeSupplierJob(param, VehicleCompanyEnum.VEHICLE_COMPANY_2)) {
                rawDataZelosBusinessService.getZelosDataInfo();
            }

            XxlJobHelper.log("同步无人车的实时信息 Job任务结束 Time: " + new Date());
        } catch (Exception exce) {
            log.error("同步无人车的实时信息执行发生异常！", exce);
        }
    }
}
