package dst.v2x.business.slv.service.module.business.monitor.service;

import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.VehicleInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.VehicleInfoPageQueryVO;
import dst.v2x.business.slv.service.infrastructure.biz.rtmp.vo.RtmpVideoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 车辆监控-视频-相关服务
 *
 * @author: pengyunlin
 * @date: 2025/9/10 15:51
 */
@Service
@Slf4j
public class MonitorVideoBusinessService {
    @Autowired
    private VehicleInfoServiceImpl vehicleInfoService;
    @Autowired
    private MonitorVideoNeolixBusinessService monitorVideoNeolixBusinessService;

    /**
     * 查询视频列表
     */
    public Response<List<RtmpVideoVO>> getVideoList(String vehicleNo){
        VehicleInfo vehicleInfo = vehicleInfoService.queryOne(vehicleNo);
        if (vehicleInfo == null) {
            return Response.error("车辆信息不存在");
        }
        if(VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode().equals(vehicleInfo.getBelongCompany())){
            return monitorVideoNeolixBusinessService.getVideoList(vehicleInfo.getVehicleVin());
        }
        return Response.error("该车暂不支持视频查看功能");
    }

    /**
     * 关闭视频
     */
    public Response<?> stopVideo(String vehicleNo, List<String> videoChannelList){
        VehicleInfo vehicleInfo = vehicleInfoService.queryOne(vehicleNo);
        if (vehicleInfo == null) {
            return Response.error("车辆信息不存在");
        }
        if(VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode().equals(vehicleInfo.getBelongCompany())){
            return monitorVideoNeolixBusinessService.stopVideo(vehicleInfo.getVehicleVin(), videoChannelList);
        }
        return Response.error("该车暂不支持视频关闭功能");
    }
}
