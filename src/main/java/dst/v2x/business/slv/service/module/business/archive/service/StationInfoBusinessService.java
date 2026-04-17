package dst.v2x.business.slv.service.module.business.archive.service;

import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.StationInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.StationInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.VehicleInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.StationInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.StationInfoPageQueryVO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.VehicleInfoPageQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 档案信息-站点信息相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Service
@Slf4j
public class StationInfoBusinessService {
    @Autowired
    private StationInfoServiceImpl stationInfoService;
    @Autowired
    private VehicleInfoServiceImpl vehicleInfoService;
    @Autowired
    private StationInfoNeolixBusinessService stationInfoNeolixBusinessService;

    /**
     * 分页查询站点信息
     *
     * @param queryDTO
     * @return
     */
    public PageDTO<StationInfoPageQueryVO> queryPage(StationInfoPageQueryDTO queryDTO) {
        return stationInfoService.queryPage(queryDTO);
    }

    /**
     * 获取站点信息-实时
     */
    public Response<List<StationInfo>> getStationInfosByRealtime(String vehicleNo) {
        VehicleInfo vehicleInfo = vehicleInfoService.queryOne(vehicleNo);
        if (vehicleInfo == null) {
            return Response.error("车辆信息不存在");
        }
        if(VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode().equals(vehicleInfo.getBelongCompany())){
            return stationInfoNeolixBusinessService.getStationInfosByRealtime(vehicleInfo.getVehicleVin());
        }
        return Response.error("该车暂不支持实时获取站点信息");
    }
}
