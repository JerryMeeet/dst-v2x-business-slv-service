package dst.v2x.business.slv.service.module.business.archive.service;

import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.VehicleInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.VehicleInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.VehicleInfoPageQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 档案信息-车辆信息相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Service
@Slf4j
public class VehicleInfoBusinessService {
    @Autowired
    private VehicleInfoServiceImpl vehicleInfoService;
    @Autowired
    private VehicleInfoNeolixBusinessService vehicleInfoNeolixBusinessService;

    /**
     * 分页查询车辆信息
     *
     * @param queryDTO
     * @return
     */
    public PageDTO<VehicleInfoPageQueryVO> queryPage(VehicleInfoPageQueryDTO queryDTO) {
        return vehicleInfoService.queryPage(queryDTO);
    }

    /**
     * 查询车辆信息
     *
     * @param vehicleNo
     * @return
     */
    public VehicleInfoPageQueryVO queryOne(String vehicleNo) {
        return vehicleInfoService.queryOneVO(vehicleNo);
    }

    /**
     * 获取车辆型号列表
     */
    public List<String> queryTypeList() {
        return vehicleInfoService.queryTypeList();
    }

    /**
     * 上下电
     */
    public Response<?> powerOnOff(String vehicleNo, Boolean isOn){
        VehicleInfo vehicleInfo = vehicleInfoService.queryOne(vehicleNo);
        if (vehicleInfo == null) {
            return Response.error("车辆信息不存在");
        }
        if(VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode().equals(vehicleInfo.getBelongCompany())){
            return vehicleInfoNeolixBusinessService.powerOnOff(vehicleInfo.getVehicleVin(), isOn);
        }
        return Response.error("该车暂不支持上下电");
    }

    /**
     * 开门
     */
    public Response<?> openDoor(String vehicleNo){
        VehicleInfo vehicleInfo = vehicleInfoService.queryOne(vehicleNo);
        if (vehicleInfo == null) {
            return Response.error("车辆信息不存在");
        }
        if(VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode().equals(vehicleInfo.getBelongCompany())){
            return vehicleInfoNeolixBusinessService.openDoor(vehicleInfo.getVehicleVin(),vehicleInfo.getCabinetCode());
        }
        return Response.error("该车暂不支持开门");
    }
}
