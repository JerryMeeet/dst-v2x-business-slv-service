package dst.v2x.business.slv.service.infrastructure.acl.neolix.service;

import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.enmus.NeolixTaskStatusEnum;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixStationInfoRes;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixTaskInfoRes;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.TaskGoalDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.StationInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.TaskInfo;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author pengyl
 * @date 2025/7/3
 * @description 新石器无人车数据转换服务
 */
@Service
public class NeolixConvertService {
    /**
     * 任务信息转换
     */
    public TaskInfo convertTaskInfo(NeolixTaskInfoRes neolixTaskInfoRes) {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskNo(String.valueOf(neolixTaskInfoRes.getMissionId()));
        taskInfo.setVin(neolixTaskInfoRes.getVin());
        //将三方状态转换成内部状态
        taskInfo.setTaskStatus(NeolixTaskStatusEnum.getTaskStatusCode(neolixTaskInfoRes.getShowStatus()));
        taskInfo.setStartTime(neolixTaskInfoRes.getStartTime());
        taskInfo.setEndTime(neolixTaskInfoRes.getEstimateArriveTime());

        //构建途径点信息
        TaskGoalDTO taskGoalDTO = new TaskGoalDTO();
        taskGoalDTO.setGoalNo(taskInfo.getTaskNo());
        taskGoalDTO.setStartLat(neolixTaskInfoRes.getStartLat());
        taskGoalDTO.setStartLng(neolixTaskInfoRes.getStartLng());
        taskGoalDTO.setTargetLat(neolixTaskInfoRes.getTargetLat());
        taskGoalDTO.setTargetLng(neolixTaskInfoRes.getTargetLng());
        taskGoalDTO.setStartTime(neolixTaskInfoRes.getStartTime());
        taskGoalDTO.setEstimateArriveTime(neolixTaskInfoRes.getEstimateArriveTime());
        taskInfo.setGoals(DstJsonUtil.toString(Arrays.asList(taskGoalDTO)));
        return taskInfo;
    }

    /**
     * 站点信息转换
     */
    public StationInfo convertStationInfo(NeolixStationInfoRes neolixStationInfoRes) {
        StationInfo stationInfo = new StationInfo();
        stationInfo.setStationNo(String.valueOf(neolixStationInfoRes.getStationId()));
        stationInfo.setStationName(neolixStationInfoRes.getStationName());
        stationInfo.setStationAddress(neolixStationInfoRes.getStationAddress());
        stationInfo.setLat(neolixStationInfoRes.getLat());
        stationInfo.setLng(neolixStationInfoRes.getLng());
        return stationInfo;
    }
}
