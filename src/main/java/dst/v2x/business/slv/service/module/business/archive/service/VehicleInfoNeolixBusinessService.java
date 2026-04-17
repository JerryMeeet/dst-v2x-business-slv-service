package dst.v2x.business.slv.service.module.business.archive.service;

import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.api.NeolixRemoteApi;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.request.NeolixPowerOnOffReq;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.request.NeolixTaskInfoReq;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixResponse;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixVehicleBaseListRes;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.service.NeolixRemoteApiService;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.VehicleInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 档案信息-车辆信息-新石器相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Service
@Slf4j
public class VehicleInfoNeolixBusinessService {
    @Autowired
    private VehicleInfoServiceImpl vehicleInfoService;
    @Autowired
    private NeolixRemoteApiService neolixRemoteApiService;
    @Autowired
    private NeolixRemoteApi neolixRemoteApi;

    /**
     * 拉取新石器无人车的车辆信息
     */
    public void updateNeolixVehicleInfo() {
        try {
            log.info("开始拉取新石器无人车的车辆信息");
            List<NeolixVehicleBaseListRes> vehicleResultList = neolixRemoteApiService.getVehicleBaseList();

            //转换数据
            List<VehicleInfo> vehicleInfos = convertNeolixVehicleInfoRes(vehicleResultList);

            //分批处理
            int batchSize = 100;
            int totalBatches = (int) Math.ceil((double) vehicleInfos.size() / batchSize);
            IntStream.range(0, totalBatches)
                    .mapToObj(i -> vehicleInfos.subList(
                            i * batchSize,
                            Math.min((i + 1) * batchSize, vehicleInfos.size())
                    ))
                    .forEach(this::batchProcessVehicle);
            log.info("结束拉取新石器无人车的车辆信息");
        } catch (Exception e) {
            log.error("拉取新石器无人车的车辆信息，发生异常!", e);
        }
    }

    /**
     * 批量处理车辆数据
     */
    private void batchProcessVehicle(List<VehicleInfo> vehicleInfos){
        //批量入库
        vehicleInfoService.batchAddOrUpdate(vehicleInfos);
    }

    /**
     * 新石器车辆信息转换为数据车辆信息对象
     */
    private List<VehicleInfo> convertNeolixVehicleInfoRes(List<NeolixVehicleBaseListRes> neolixVehicles){
        return neolixVehicles.stream()
                .filter(d->StringUtils.isNotBlank(d.getVehicleNo()))
                .map(d->{
            VehicleInfo vehicleInfo = new VehicleInfo();
            BeanUtils.copyProperties(d, vehicleInfo);
            //载重 (单位 KG)
            vehicleInfo.setCarryingCapacity(String.valueOf(d.getCarryingCapacity()));
            //电池使用年限
            vehicleInfo.setBatteryLifeYear(String.valueOf(d.getBatteryLifeYear()));
            //车辆满载里程单位：km
            vehicleInfo.setEnduranceMileage(String.valueOf(d.getEnduranceMileage()));
            //空间大小
            vehicleInfo.setInterspace(String.valueOf(d.getInterspace()));
            vehicleInfo.setBelongCompany(VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode());
            return vehicleInfo;
        }).collect(Collectors.toList());
    }

    /**
     * 上下电
     */
    public Response<?> powerOnOff(String vin, Boolean isOn){
        if (StringUtils.isEmpty(vin)) {
            return Response.error("VIN不能为空");
        }
        NeolixPowerOnOffReq req = new NeolixPowerOnOffReq();
        req.setVin(vin);
        req.setHandleType(isOn!=null&&isOn ? 1 : 2);
        NeolixResponse<?> apiResponse = neolixRemoteApi.powerOnOff(req);
        if (!apiResponse.getSuccess()) {
            return Response.error(apiResponse.getMsg());
        }
        return Response.succeed();
    }

    /**
     * 开门
     */
    public Response<?> openDoor(String vin,String cabinetCode){
        if (StringUtils.isEmpty(vin)) {
            return Response.error("VIN不能为空");
        }
        NeolixTaskInfoReq req = new NeolixTaskInfoReq();
        req.setVin(vin);
        req.setCabinetCode(cabinetCode);
        NeolixResponse<?> apiResponse = neolixRemoteApi.cabinetOpen(req);
        if (!apiResponse.getSuccess()) {
            return Response.error(apiResponse.getMsg());
        }
        return Response.succeed();
    }
}
