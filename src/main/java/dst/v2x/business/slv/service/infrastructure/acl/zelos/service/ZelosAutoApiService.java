package dst.v2x.business.slv.service.infrastructure.acl.zelos.service;

import cn.hutool.core.bean.BeanUtil;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.enums.archive.VehicleDoorStatusEnum;
import dst.v2x.business.slv.service.common.utils.LangUtil;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.api.ZelosAutoCarAuthFeignApi;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.api.ZelosAutoCarFeignApi;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.convert.ZelosErrorStatusConvert;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.request.ZelosGetAutoVehiclertInfoReq;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.request.ZelosTokenReq;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.*;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.VehicleInfo;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: zy
 * @date: 2024/11/8 15:57
 */
@Slf4j
@Service
public class ZelosAutoApiService {
    @Value("${zelos.appId}")
    private String zelosAppId; // 应用id
    @Value("${zelos.appKey}")
    private String zelosAppKey; // 应用密钥
    @Resource
    private ZelosAutoCarAuthFeignApi zelosAutoCarAuthFeignApi;
    @Resource
    private ZelosAutoCarFeignApi zelosAutoCarFeignApi;

    /**
     * 获取zelos的accessToken
     * @return
     */
    public ZelosTokenRes getAccessToken(){
        ZelosTokenReq req = new ZelosTokenReq();
        req.setAppId(zelosAppId);
        req.setAppKey(zelosAppKey);
        ZelosResponse<ZelosTokenRes> accessToken = zelosAutoCarAuthFeignApi.getAccessToken(req);
        return accessToken.getData();
    }

    /**
     * 根据车辆编号查询车辆信息
     * @param vehicleNos
     * @return
     */
    public List<VehicleInfo> getAutoVehicleInfo(List<String> vehicleNos){
        ZelosGetAutoVehiclertInfoReq req = new ZelosGetAutoVehiclertInfoReq();
        req.setVehicleNoList(vehicleNos);
        ZelosResponse2<List<ZelosVehicleInfoRes>> vehicleInfoResponse = zelosAutoCarFeignApi.getAutoVehicleInfo(req);
        if(vehicleInfoResponse == null){
            return new ArrayList<>();
        }
        return convertZelosVehicleInfoRes(vehicleInfoResponse.getData());
    }

    /**
     * 根据车辆编号查询车辆实时动态信息
     * @param vehicleNos
     * @return
     */
    public List<RawData> getAutoVehiclertInfo(List<String> vehicleNos){
        ZelosGetAutoVehiclertInfoReq req = new ZelosGetAutoVehiclertInfoReq();
        req.setVehicleNoList(vehicleNos);

        ZelosResponse2<List<GetAutoVehiclertInfoRes>> autoVehiclertInfo = zelosAutoCarFeignApi.getAutoVehiclertInfo(req);
        if(autoVehiclertInfo == null || CollectionUtils.isEmpty(autoVehiclertInfo.getData())){
            return new ArrayList<>();
        }
        return autoVehiclertInfo.getData().stream().map(d->convertZelosVehiclertInfoRes(d)).collect(Collectors.toList());
    }

    /**
     * 车辆编码是否有效
     * @param vehicleNo
     * @return
     */
    private boolean vehicleNoIsValid(String vehicleNo){
        ZelosGetAutoVehiclertInfoReq req = new ZelosGetAutoVehiclertInfoReq();
        req.setVehicleNoList(Arrays.asList(vehicleNo));

        ZelosResponse2<List<GetAutoVehiclertInfoRes>> autoVehiclertInfo = zelosAutoCarFeignApi.getAutoVehiclertInfo(req);
        if(autoVehiclertInfo == null || CollectionUtils.isEmpty(autoVehiclertInfo.getData())){
            return false;
        }
        return true;
    }

    /**
     * 根据传入的车辆编号返回已删除的车辆编号
     */
    public List<String> getInvalidVehicleNo(List<String> vehicleNos) {
        return vehicleNos.stream()
                .filter(d->!vehicleNoIsValid(d))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * 九识车辆信息转换为数据车辆上报数据对象
     *
     * @param vehiclertInfo
     * @return RawData
     * @author 江民来
     * @date 2025/2/12 18:37
     */
    private RawData convertZelosVehiclertInfoRes(GetAutoVehiclertInfoRes vehiclertInfo) {
        RawData raw = new RawData();
        BeanUtil.copyProperties(vehiclertInfo, raw);
        //车速
        raw.setSpeed(String.valueOf(vehiclertInfo.getSpeed()));
        //电量
        raw.setElectricCity(vehiclertInfo.getElectricCity());
        //车门状态
        raw.setDoorStatus(convertZelosDoorStatus(vehiclertInfo.getDoorStatusMap()));
        //总里程数
        raw.setOdometer(LangUtil.parseDouble(vehiclertInfo.getOdometer()));
        //车辆任务里程数
        raw.setTaskDistance(LangUtil.parseDouble(vehiclertInfo.getTaskDistance()));
        //电池健康状态
        raw.setBatteryHealth(LangUtil.parseInteger(vehiclertInfo.getBatteryHealth()));
        //左前胎压
        raw.setTplf(LangUtil.parseInteger(vehiclertInfo.getTplf()));
        //左后胎压
        raw.setTplb(LangUtil.parseInteger(vehiclertInfo.getTplb()));
        //右前胎压
        raw.setTprf(LangUtil.parseInteger(vehiclertInfo.getTprf()));
        //右后胎压
        raw.setTprb(LangUtil.parseInteger(vehiclertInfo.getTprb()));
        //车辆故障状态
        if(StringUtils.isNotBlank(vehiclertInfo.getErrorStatusName())){
            raw.setErrorStatus(ZelosErrorStatusConvert.getVehicleErrorStatusCode(vehiclertInfo.getErrorStatusName()));
        }

        // 推送时间取当前值
        LocalDateTime now = LocalDateTime.now();
        raw.setPullTime(now);
        raw.setDataTime(now);
        return raw;
    }

    /**
     * 车门状态转换，将九识车辆信息中的车门状态转换为数据车辆上报数据对象中的车门状态
     *  "DOOR_NONE", "未知"
     *  "DOOR_OPEN", "开启"
     *  "DOOR_CLOSE", "关闭"
     *  "DOOR_ERROR", "异常"
     */
    private Integer convertZelosDoorStatus(Map<String, String> doorStatusMap) {
        if(doorStatusMap == null){
            return null;
        }

        //门总数量
        int totalNum = doorStatusMap.size();
        //关闭的门数量
        int closeNum = 0;
        //开启的门数量
        int openNum = 0;

        Collection<String> doorStatus = doorStatusMap.values();
        for(String doorStatu:doorStatus){
            if(doorStatu.equals("DOOR_OPEN")){
                openNum++;
            }else if(doorStatu.equals("DOOR_CLOSE")){
                closeNum++;
            }
        }

        if(totalNum == closeNum){
            //所有门都关闭=门已全部关闭
            return VehicleDoorStatusEnum.DOOR_1.getCode();
        }else if(openNum>0){
            //有一个门开启=门未全部关闭
            return VehicleDoorStatusEnum.DOOR_0.getCode();
        }
        return null;
    }

    /**
     * 九识车辆信息转换为数据车辆信息对象
     */
    private List<VehicleInfo> convertZelosVehicleInfoRes(List<ZelosVehicleInfoRes> zelosVehicles){
        if(CollectionUtils.isEmpty(zelosVehicles)){
            return new ArrayList<>();
        }
        return zelosVehicles.stream().map(d->{
            VehicleInfo vehicleInfo = new VehicleInfo();
            BeanUtil.copyProperties(d, vehicleInfo);
            vehicleInfo.setBelongCompany(VehicleCompanyEnum.VEHICLE_COMPANY_2.getCode());
            return vehicleInfo;
        }).collect(Collectors.toList());
    }
}
