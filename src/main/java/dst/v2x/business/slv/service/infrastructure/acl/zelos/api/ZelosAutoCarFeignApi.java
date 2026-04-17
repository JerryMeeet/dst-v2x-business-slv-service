package dst.v2x.business.slv.service.infrastructure.acl.zelos.api;

import dst.v2x.business.slv.service.common.feign.configuration.ZelosFeignClientConfiguration;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.request.*;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.*;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.can.ZelosCanRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 九识无人车接口调用
 *
 * @author 江民来
 * @date 2025/6/13 11:08
 */
@FeignClient(name = "ZelosAutoCarFeignApi", url = "${zelos.remote.url}", contextId = "ZelosAutoCarFeignApi", configuration = ZelosFeignClientConfiguration.class)
public interface ZelosAutoCarFeignApi {
    /**
     * 获取九识无人车的车辆列表-新版接口
     */
    @PostMapping("/admin-server/dst/autoVehicle/getAutoVehicleList")
    ZelosResponse2<ZelosPageRes> getAutoVehicleList(@RequestBody ZelosGetVehicleListReq req);

    /**
     * 获取车辆档案信息-新版接口
     */
    @PostMapping("/admin-server/dst/autoVehicle/getAutoVehicleInfo")
    ZelosResponse2<List<ZelosVehicleInfoRes>> getAutoVehicleInfo(@RequestBody ZelosGetAutoVehiclertInfoReq req);

    /**
     * 获取车辆实时动态信息-新版接口
     */
    @PostMapping("/admin-server/dst/autoVehicle/getAutoVehiclertInfo")
    ZelosResponse2<List<GetAutoVehiclertInfoRes>> getAutoVehiclertInfo(@RequestBody ZelosGetAutoVehiclertInfoReq req);

    /**
     * 获取⻋辆预警&报警信息
     *
     * @author 江民来
     * @date 2025/6/13 14:18
     */
    @PostMapping("/admin-server/dst/autoVehicle/getAlarmInfo")
    ZelosResponse2<List<ZelosAlarmRes>> getZelosAlarmInfo(@RequestBody ZelosAlarmReq req);

    /**
     * 获取车辆CAN数据
     */
    @PostMapping("/admin-server/dst/autoVehicle/getCanData")
    ZelosResponse2<List<ZelosCanRes>> getZelosCanData(@RequestBody ZelosCanReq req);

    /**
     * 获取路线列表
     */
    @GetMapping("/business-server/open-apis/mapMake/list")
    ZelosResponse<ZelosTotalListRes<ZelosMapMakeRes>> getZelosMapMakeList();

    /**
     * 获取站点列表-分页
     */
    @PostMapping("/admin-server/dst/autoVehicle/getSiteList")
    ZelosResponse2<ZelosPageRes> getZelosSitePage(@RequestBody ZelosGetVehicleListReq req);

    /**
     * 获取站点信息
     */
    @PostMapping("/admin-server/dst/autoVehicle/getSiteInfo")
    ZelosResponse2<List<ZelosSiteInfoRes>> getSiteInfos(@RequestBody ZelosGetSiteInfoReq req);

    /**
     * 获取任务列表-分页
     */
    @PostMapping("/admin-server/dst/autoVehicle/getTaskList")
    ZelosResponse2<ZelosPageRes> getZelosTaskPage(@RequestBody ZelosGetVehicleListReq req);

    /**
     * 获取任务信息
     */
    @PostMapping("/admin-server/dst/autoVehicle/getTaskInfo")
    ZelosResponse2<List<ZelosTaskInfoRes>> getTaskInfos(@RequestBody ZelosGetTaskInfoReq req);

}
