package dst.v2x.business.slv.service.module.business.archive.service;

import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.utils.StatusDescUtil;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.api.NeolixRemoteApi;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.request.NeolixPowerOnOffReq;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.request.NeolixTaskInfoAddReq;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.request.NeolixTaskInfoBySubIdReq;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.request.NeolixTaskInfoReq;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixResponse;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixTaskInfoAddRes;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixTaskInfoBySubIdRes;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixTaskInfoCurrentRes;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.service.NeolixRemoteApiService;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.VehicleInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.StationInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.TaskInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.StationInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.TaskInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.TaskInfoCurrentQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 档案信息-任务信息-新石器相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Service
@Slf4j
public class TaskInfoNeolixBusinessService {
    @Autowired
    private VehicleInfoServiceImpl vehicleInfoService;
    @Autowired
    private TaskInfoServiceImpl taskInfoService;
    @Autowired
    private NeolixRemoteApiService neolixRemoteApiService;
    @Autowired
    private NeolixRemoteApi neolixRemoteApi;
    @Autowired
    private StationInfoServiceImpl stationInfoService;
    @Value("${neolix.business}")
    private String business;

    /**
     * 拉取新石器无人车的任务信息
     */
    public void updateNeolixTaskInfo() {
        try {
            log.info("开始拉取新石器无人车的任务信息");
            // 创建新石器车辆信息查询对象
            VehicleInfoPageQueryDTO pageDTO = new VehicleInfoPageQueryDTO();
            pageDTO.setBelongCompany(VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode());
            //初始化页码
            int pageNo = 1;
            //初始化总页数
            long totalPage = 0;
            //设置每页查询数量
            pageDTO.setPageSize(200);

            //按照页码循环查询
            while (true) {
                pageDTO.setPageNum(pageNo);
                //分页请求新石器获取车辆信息
                PageDTO<String> pageRes = vehicleInfoService.queryVinPage(pageDTO);

                // 如果是第一页，记录总页数
                if (pageNo == 1) {
                    totalPage = pageRes.getTotalPage();
                    log.info("总页数：{}", totalPage);
                }

                // 输出当前查询页码
                log.info("当前请求页数：{}", pageNo);

                List<String> vehicleVinList = pageRes.getList();

                //根据vin获取任务信息
                vehicleVinList.forEach(vin->{
                    //调用新石器接口获取任务信息
                    List<TaskInfo> taskList = neolixRemoteApiService.getTaskList(vin);

                    //批量入库
                    taskInfoService.batchAddOrUpdate(taskList, VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode());
                });

                //增加页码
                pageNo++;

                // 如果超过总页数，结束循环
                if (pageNo > totalPage) {
                    break;
                }
            }
            log.info("结束拉取新石器无人车的任务信息");
        } catch (Exception e) {
            log.error("拉取新石器无人车的任务信息，发生异常!", e);
        }
    }

    /**
     * 新增任务
     */
    public Response<?> add(String vin, String departureStationId, String destinationStationId) {
        if (StringUtils.isEmpty(vin)) {
            return Response.error("VIN不能为空");
        }
        NeolixTaskInfoAddReq req = new NeolixTaskInfoAddReq();
        req.setVin(vin);
        req.setDepartureStationId(departureStationId);
        req.setDestinationStationId(destinationStationId);
        req.setBusiness(business);
        NeolixResponse<NeolixTaskInfoAddRes> apiResponse = neolixRemoteApi.vehicleStart(req);
        if (!apiResponse.getSuccess()) {
            return Response.error(apiResponse.getMsg());
        }
        return Response.succeed();
    }

    /**
     * 取消任务
     */
    public Response<?> cancel(String vin){
        if (StringUtils.isEmpty(vin)) {
            return Response.error("VIN不能为空");
        }
        /*NeolixTaskInfoReq req = new NeolixTaskInfoReq();
        req.setVin(vin);*/
        NeolixResponse<?> apiResponse = neolixRemoteApi.vehicleCancel(vin);
        if (!"10000".equals(apiResponse.getCode())) {
            return Response.error(apiResponse.getMsg());
        }
        return Response.succeed();
    }

    /**
     * 查询当前任务
     */
    public Response<TaskInfoCurrentQueryVO> queryCurrentTask(String vin){
        if (StringUtils.isEmpty(vin)) {
            return Response.error("VIN不能为空");
        }
        NeolixTaskInfoReq req = new NeolixTaskInfoReq();
        req.setVin(vin);
        //调用三方接口获取当前任务信息
        NeolixResponse<NeolixTaskInfoCurrentRes> apiResponse = neolixRemoteApi.getCurrentMission(req);
        if (!apiResponse.getSuccess()) {
            return Response.error(apiResponse.getMsg());
        }
        NeolixTaskInfoCurrentRes apiRes = apiResponse.getData();
        if(apiRes == null){
            return Response.succeed(null);
        }

        // 调用第三方接口获取结束时间和执行时间
        NeolixTaskInfoBySubIdReq taskInfoBySubIdReq = new NeolixTaskInfoBySubIdReq();
        taskInfoBySubIdReq.setMissionSubId(apiRes.getMissionSubId());
        NeolixResponse<NeolixTaskInfoBySubIdRes> resNeolixResponse = neolixRemoteApi.getMissionBySubId(taskInfoBySubIdReq);


        //根据任务信息返回的站点ID从数据库中获取站点明细，经产品确认站点明细可接受延迟
        String startStationNo = String.valueOf(apiRes.getStartStationId());
        String targetStationNo = String.valueOf(apiRes.getEndStationId());
        Map<String, StationInfo> stationMap = stationInfoService.getMapByStationNo(Arrays.asList(startStationNo, targetStationNo),
                VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode());
        StationInfo startStationInfo = stationMap.get(startStationNo);
        StationInfo targetStationInfo = stationMap.get(targetStationNo);

        // 将三方接口返回对象转换成VO
        TaskInfoCurrentQueryVO vo = new TaskInfoCurrentQueryVO();
        vo.setTaskNo(String.valueOf(apiRes.getMissionSubId()));
        vo.setTaskStatus(apiRes.getMissionStatus());
        if(startStationInfo != null){
            vo.setStartLat(startStationInfo.getLat());
            vo.setStartLng(startStationInfo.getLng());
        }
        if(targetStationInfo != null){
            vo.setTargetLat(targetStationInfo.getLat());
            vo.setTargetLng(targetStationInfo.getLng());
        }

        // 封装结束时间和执行时间
        if (resNeolixResponse.getSuccess() && resNeolixResponse.getData() != null) {
            vo.setFinishTime(resNeolixResponse.getData().getFinishTime());
            vo.setExecuteTime(resNeolixResponse.getData().getExecuteTime());
        }

        StatusDescUtil.convertStatusDesc(vo);
        return Response.succeed(vo);
    }
}
