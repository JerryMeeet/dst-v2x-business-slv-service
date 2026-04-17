package dst.v2x.business.slv.service.infrastructure.acl.zelos.service;

import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.common.enums.archive.TaskStatusEnum;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.utils.LangUtil;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.api.ZelosAutoCarFeignApi;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.convert.ZelosTimeConvert;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.request.ZelosGetTaskInfoReq;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosResponse2;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosTaskGoalRes;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosTaskInfoRes;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.TaskGoalDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.TaskInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 九识数据相关接口-任务
 */
@Slf4j
@Service
public class ZelosTaskApiService {
    @Resource
    private ZelosAutoCarFeignApi zelosAutoCarFeignApi;
    @Resource
    private VehicleInfoServiceImpl vehicleInfoService;

    /**
     * 根据任务编号查询任务信息
     * @param taskNos
     * @return
     */
    public List<TaskInfo> getTaskInfos(List<String> taskNos){
        ZelosGetTaskInfoReq req = new ZelosGetTaskInfoReq();
        req.setTaskNoIds(taskNos);
        ZelosResponse2<List<ZelosTaskInfoRes>> vehicleInfoResponse = zelosAutoCarFeignApi.getTaskInfos(req);
        if(vehicleInfoResponse == null){
            return new ArrayList<>();
        }
        return convertZelosTaskInfoRes(vehicleInfoResponse.getData());
    }

    /**
     * 九识任务信息转换为数据库对象
     */
    private List<TaskInfo> convertZelosTaskInfoRes(List<ZelosTaskInfoRes> zelosTaskInfos){
        if(CollectionUtils.isEmpty(zelosTaskInfos)){
            return new ArrayList<>();
        }
        //从任务信息中提取车辆编号
        List<String> vehicleNos = zelosTaskInfos.stream().map(ZelosTaskInfoRes::getVehicleNo).collect(Collectors.toList());

        //根据车辆编号获取车辆信息map
        Map<String, String> vehicleNoMap = vehicleInfoService.getVinMapByVehicleNo(vehicleNos);

        return zelosTaskInfos.stream().map(d->{
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTaskNo(d.getTaskNo());
            taskInfo.setVin(vehicleNoMap.get(d.getVehicleNo()));
            taskInfo.setTaskStatus(convertZelosTaskStatus(d.getTaskStatus()));
            taskInfo.setBelongCompany(VehicleCompanyEnum.VEHICLE_COMPANY_2.getCode());
            taskInfo.setStartTime(ZelosTimeConvert.convertZelosTime(d.getStartTime()));
            taskInfo.setEndTime(ZelosTimeConvert.convertZelosTime(d.getEndTime()));
            taskInfo.setGoals(DstJsonUtil.toString(convertZelosTaskGoalRes(d.getGoals())));
            return taskInfo;
        }).filter(v-> StringUtils.isNotBlank(v.getVin()) && v.getTaskStatus() != null)
                .collect(Collectors.toList());
    }

    /**
     * 途径信息转发,将九识任务途径点信息转换为数据库任务途径点信息
     */
    private List<TaskGoalDTO> convertZelosTaskGoalRes(List<ZelosTaskGoalRes> goals){
        if(CollectionUtils.isEmpty(goals)){
            return new ArrayList<>();
        }
        return goals.stream().map(d->{
            TaskGoalDTO taskGoalDTO = new TaskGoalDTO();
            taskGoalDTO.setGoalNo(d.getGoalNo());
            taskGoalDTO.setStartLat(LangUtil.parseDouble(d.getStartLat()));
            taskGoalDTO.setStartLng(LangUtil.parseDouble(d.getStartLng()));
            taskGoalDTO.setTargetLat(LangUtil.parseDouble(d.getTargetLat()));
            taskGoalDTO.setTargetLng(LangUtil.parseDouble(d.getTargetLng()));
            taskGoalDTO.setStartTime(ZelosTimeConvert.convertZelosTime(d.getStartTime()));
            taskGoalDTO.setEstimateArriveTime(ZelosTimeConvert.convertZelosTime(d.getEstimateArriveTime()));
            taskGoalDTO.setArriveTime(ZelosTimeConvert.convertZelosTime(d.getArriveTime()));
            return taskGoalDTO;
        }).collect(Collectors.toUnmodifiableList());
    }

    /**
     * 任务状态转换,将九识任务状态转换为数据库任务状态
     */
    private Integer convertZelosTaskStatus(String taskStatus) {
        switch (taskStatus) {
            case "已创建":
                return TaskStatusEnum.STATUS_1.getCode();
            case "执⾏中":
                return TaskStatusEnum.STATUS_2.getCode();
            case "已完成":
                return TaskStatusEnum.STATUS_3.getCode();
            case "已取消":
                return TaskStatusEnum.STATUS_4.getCode();
            default:
                return null;
        }
    }
}
