package dst.v2x.business.slv.service.infrastructure.acl.whiterhino.service;

import cn.hutool.core.collection.CollUtil;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.api.WhiteRhinoRemoteApi;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.request.WhiteRhinoVehicleInfoReq;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.response.WhiteRhinoVehicleInfoRes;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: zy
 * @date: 2024/11/8 15:57
 */
@Slf4j
@Service
public class WhiteRhinoRemoteApiService {
    @Resource
    private WhiteRhinoRemoteApi whiteRhinoRemoteApi;

    /**
     * 根据车牌号获取车辆信息Map，key为车牌号，value为车辆信息
     * @param vehicleNoList
     * @return
     */
    public Map<String, WhiteRhinoVehicleInfoRes> getVehicleInfoMap(List<String> vehicleNoList){
        //根据车牌号获取车辆信息
        WhiteRhinoVehicleInfoReq infoDTO = new WhiteRhinoVehicleInfoReq();
        infoDTO.setVehicleNoList(vehicleNoList);
        Response<List<WhiteRhinoVehicleInfoRes>> vehicleInfoResponse = whiteRhinoRemoteApi.getVehicleInfo(infoDTO);
        if(vehicleInfoResponse == null || vehicleInfoResponse.getData() == null){
            return new HashMap<>();
        }
        List<WhiteRhinoVehicleInfoRes> vehicleInfoList = vehicleInfoResponse.getData();
        if(CollUtil.isEmpty(vehicleInfoList)){
            return new HashMap<>();
        }
        return vehicleInfoList.stream().collect(Collectors.toMap(WhiteRhinoVehicleInfoRes::getVehicleNo, Function.identity(), (o1, o2) -> o1));
    }
}
