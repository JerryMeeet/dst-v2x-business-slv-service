package dst.v2x.business.slv.service.infrastructure.acl.neolix.api;

import dst.v2x.business.slv.service.common.feign.configuration.NeolixFeignClientConfiguration;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.request.*;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 新石器无人车 接口请求
 */
@FeignClient(name = "neolixRemoteApi", url = "${neolix.remote.url}", contextId = "NeolixRemoteApi", configuration = NeolixFeignClientConfiguration.class)
public interface NeolixRemoteApi {

    /**
     * 获取车辆基础信息列表
     **/
    @PostMapping(value = "/slvapi/getVehicleBaseList")
    NeolixResponse<List<NeolixVehicleBaseListRes>> getVehicleBaseList(@RequestBody NeolixVehicleBaseListReq infoDTO);

    /**
     * 批量获取实时基础信息
     */
    @PostMapping(value = "/slvapi/batchGetVehicleList")
    NeolixResponse<List<NeolixVehicleInfoRes>> batchGetVehicleList(@RequestBody List<String> req);

    /**
     * 获取车辆报警信息
     *
     * @author 江民来
     * @date 2025/6/12 15:46
     */
    @PostMapping("/slvapi/vehicleFault")
    NeolixResponse<VehicleFaultRes> getVehicleFault(@RequestBody NeolixVehicleInfoReq infoDTO);

    /**
     * 获取站点信息
     */
    @PostMapping("/slvapi/stationInfoManagement")
    NeolixResponse<List<NeolixStationInfoRes>> getStationList(@RequestBody NeolixStationInfoReq infoDTO);

    /**
     * 获取任务信息
     */
    @PostMapping("/slvapi/missionInfoManagement")
    NeolixResponse<List<NeolixTaskInfoRes>> getTaskList(@RequestBody NeolixTaskInfoReq infoDTO);

    /**
     * 创建任务信息
     */
    @PostMapping("/slvapi/vehicleStart/v1")
    NeolixResponse<NeolixTaskInfoAddRes> vehicleStart(@RequestBody NeolixTaskInfoAddReq infoDTO);

    /**
     * 查询当前任务信息
     */
    @PostMapping("/slvapi/getCurrentMission")
    NeolixResponse<NeolixTaskInfoCurrentRes> getCurrentMission(@RequestBody NeolixTaskInfoReq infoDTO);

    /**
     * 查询任务详情
     */
    @PostMapping("/slvapi/getMissionBySubId")
    NeolixResponse<NeolixTaskInfoBySubIdRes> getMissionBySubId(@RequestBody NeolixTaskInfoBySubIdReq infoDTO);

    /**
     * 取消任务
     */
    @PostMapping("/slvapi/vehicleCancel")
    NeolixResponse<?> vehicleCancel(@RequestParam(value = "vin") String vin);

    /**
     * 开关机
     */
    @PostMapping("/slvapi/powerOnOff")
    NeolixResponse<?> powerOnOff(@RequestBody NeolixPowerOnOffReq req);

    /**
     * 车辆开门
     */
    @PostMapping("/slvapi/cabinetOpen")
    NeolixResponse<?> cabinetOpen(@RequestBody NeolixTaskInfoReq infoDTO);

    /**
     * 获取车辆列表-已作废
     **/
    @GetMapping(value = "/slvapi/getVehicleList")
    @Deprecated
    NeolixResponse<List<NeolixVehicleListRes>> getVehicleList();

    /**
     * 获取车辆详情，不支持批量-已作废
     */
    @Deprecated
    @PostMapping(value = "/slvapi/getVehicleInfo")
    NeolixResponse<NeolixVehicleInfoRes> getVehicleInfo(@RequestBody NeolixVehicleInfoReq infoDTO);
}