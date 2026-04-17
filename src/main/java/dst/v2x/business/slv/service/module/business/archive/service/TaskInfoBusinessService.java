package dst.v2x.business.slv.service.module.business.archive.service;

import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.domain.response.Response;
import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.TaskGoalDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.TaskInfoAddDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.TaskInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.TaskInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.VehicleInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.TaskInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.TaskInfoCurrentQueryVO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.TaskInfoPageQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 档案信息-任务信息相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Service
@Slf4j
public class TaskInfoBusinessService {
    @Autowired
    private TaskInfoServiceImpl taskInfoService;
    @Autowired
    private VehicleInfoServiceImpl vehicleInfoService;
    @Autowired
    private TaskInfoNeolixBusinessService taskInfoNeolixBusinessService;

    /**
     * 分页查询任务信息
     *
     * @param queryDTO
     * @return
     */
    public PageDTO<TaskInfoPageQueryVO> queryPage(TaskInfoPageQueryDTO queryDTO) {
        return taskInfoService.queryPage(queryDTO);
    }

    /**
     * 查询任务明细列表
     */
    public List<TaskGoalDTO> queryGoals(Long taskId){
        TaskInfo taskInfo = taskInfoService.getById(taskId);
        if(taskInfo == null){
            return null;
        }
        return DstJsonUtil.toList(taskInfo.getGoals(), TaskGoalDTO.class);
    }

    /**
     * 新增任务
     */
    public Response<?> add(TaskInfoAddDTO dto){
        VehicleInfo vehicleInfo = vehicleInfoService.queryOne(dto.getVehicleNo());
        if (vehicleInfo == null) {
            return Response.error("车辆信息不存在");
        }
        if(VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode().equals(vehicleInfo.getBelongCompany())){
            return taskInfoNeolixBusinessService.add(vehicleInfo.getVehicleVin(),
                    dto.getDepartureStationId(),
                    dto.getDestinationStationId());
        }
        return Response.error("该车暂不支持新增任务");
    }

    /**
     * 查询当前任务
     */
    public Response<TaskInfoCurrentQueryVO> queryCurrentTask(String vehicleNo){
        VehicleInfo vehicleInfo = vehicleInfoService.queryOne(vehicleNo);
        if (vehicleInfo == null) {
            return Response.error("车辆信息不存在");
        }
        if(VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode().equals(vehicleInfo.getBelongCompany())){
            return taskInfoNeolixBusinessService.queryCurrentTask(vehicleInfo.getVehicleVin());
        }
        return Response.error("该车暂不支持查询当前任务");
    }

    /**
     * 取消任务
     */
    public Response<?> cancel(String vehicleNo){
        VehicleInfo vehicleInfo = vehicleInfoService.queryOne(vehicleNo);
        if (vehicleInfo == null) {
            return Response.error("车辆信息不存在");
        }
        if(VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode().equals(vehicleInfo.getBelongCompany())){
            return taskInfoNeolixBusinessService.cancel(vehicleInfo.getVehicleVin());
        }
        return Response.error("该车暂不支持取消任务");
    }
}
