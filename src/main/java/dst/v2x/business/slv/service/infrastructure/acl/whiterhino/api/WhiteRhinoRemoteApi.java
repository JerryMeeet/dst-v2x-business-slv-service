package dst.v2x.business.slv.service.infrastructure.acl.whiterhino.api;

import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.feign.configuration.WhiteRhinoFeignClientConfiguration;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.request.WhiteRhinoVehicePageReq;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.request.WhiteRhinoVehicleInfoReq;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.request.WhiteRhinoVehicleRealtimeReq;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.response.VehicleRealtimeRes;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.response.WhiteRhinoVehicleInfoRes;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.response.WhiteRhinoVehiclePageRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 白犀牛无人车 接口请求
 */
@FeignClient(name ="baixiniuRemoteApi", url = "${baixiniu.remote.url}", contextId = "WhiteRhinoRemoteApi", configuration = WhiteRhinoFeignClientConfiguration.class)
public interface WhiteRhinoRemoteApi {

    /**
     * 获取车辆列表
     **/
    @PostMapping(value = "/dst/getAutoVehicleList")
    Response<WhiteRhinoVehiclePageRes> getVehicleList(@RequestBody WhiteRhinoVehicePageReq pageDTO);

    /**
     * 获取车辆详情
     **/
    @PostMapping(value = "/dst/getAutoVehicleInfo")
    Response<List<WhiteRhinoVehicleInfoRes>> getVehicleInfo(@RequestBody WhiteRhinoVehicleInfoReq infoDTO);

    /**
     * 获取车辆实时数据
     **/
    @PostMapping(value = "/dst/getRtInfo")
    Response<List<VehicleRealtimeRes>> getVehicleRealtimeData(@RequestBody WhiteRhinoVehicleRealtimeReq infoDTO);

}
