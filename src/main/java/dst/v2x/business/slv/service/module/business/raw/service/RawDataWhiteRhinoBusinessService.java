package dst.v2x.business.slv.service.module.business.raw.service;

import com.alibaba.fastjson.JSON;
import com.dst.steed.vds.common.domain.response.PageDTO;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.api.WhiteRhinoRemoteApi;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.request.WhiteRhinoVehicleRealtimeReq;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.response.VehicleRealtimeRes;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.VehicleInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 上报数据-白犀牛相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Service
@Slf4j
public class RawDataWhiteRhinoBusinessService {
    @Resource
    private WhiteRhinoRemoteApi whiteRhinoRemoteApi;
    @Autowired
    private RawDataBusinessService rawDataBusinessService;
    @Resource
    private VehicleInfoServiceImpl vehicleInfoService;

    /**
     * 每10秒一次实时拉取最新的白犀牛无人车数据
     *
     * @author 江民来
     * @date 2024/11/13 13:45
     */
    public void getWhiteRhinoVehicleDataInfo() {
        try {
            log.info("开始请求白犀牛无人车的实时数据");
            // 创建查询对象
            VehicleInfoPageQueryDTO pageDTO = new VehicleInfoPageQueryDTO();
            pageDTO.setBelongCompany(VehicleCompanyEnum.VEHICLE_COMPANY_1.getCode());
            //初始化页码
            int pageNo = 1;
            //初始化总页数
            long totalPage = 0;
            //设置每页查询数量
            pageDTO.setPageSize(200);

            //按照页码循环查询
            while (true) {
                pageDTO.setPageNum(pageNo);
                //分页请求白犀牛获取车辆信息
                PageDTO<String> pageRes = vehicleInfoService.queryNoPage(pageDTO);

                // 如果是第一页，记录总页数
                if (pageNo == 1) {
                    totalPage = pageRes.getTotalPage();
                    log.info("总页数：{}", totalPage);
                }

                // 输出当前查询页码
                log.info("当前请求页数：{}", pageNo);

                //获取上报数据
                getWhiteRhinoVehicleDataInfo(pageRes.getList());

                //增加页码
                pageNo++;

                // 如果超过总页数，结束循环
                if (pageNo > totalPage) {
                    break;
                }
            }
            log.info("白犀牛无人车实时数据请求结束");
        } catch (Exception e) {
            log.error("请求白犀牛无人车的实时数据异常", e);
        }
    }

    /**
     * 获取上报数据
     */
    private void getWhiteRhinoVehicleDataInfo(List<String> vehicleNoList){
        if(CollectionUtils.isEmpty(vehicleNoList)){
            return;
        }
        log.debug("循环请求白犀牛无人车的实时数据，车牌号集合->{}", JSON.toJSONString(vehicleNoList));
        WhiteRhinoVehicleRealtimeReq req = new WhiteRhinoVehicleRealtimeReq();
        req.setVehicleNoIds(vehicleNoList);
        List<VehicleRealtimeRes> whiteRhinoRealtimeDatas = whiteRhinoRemoteApi.getVehicleRealtimeData(req).getData();
        log.info("请求白犀牛无人车的实时数据,{}条", CollectionUtils.isEmpty(whiteRhinoRealtimeDatas)?0:whiteRhinoRealtimeDatas.size());
        if (CollectionUtils.isEmpty(whiteRhinoRealtimeDatas)) {
            return;
        }

        // 转换成保存数据的属性类
        List<RawData> rawDatas = getRawData(whiteRhinoRealtimeDatas);

        //入库
        rawDataBusinessService.insertToDB(rawDatas);

        //入redis
        rawDataBusinessService.flushRealtimeDataRedis(rawDatas);
    }

    /**
     * 组装inset数据
     *
     * @param response
     * @return List<RawData>
     * @author 江民来
     * @date 2024/11/16 11:10
     */
    private static List<RawData> getRawData(List<VehicleRealtimeRes> response) {
        LocalDateTime now = LocalDateTime.now();
        List<RawData> rawData = response.stream().map(s -> {
            RawData data = new RawData();
            data.setVehicleNo(StringUtils.isBlank(s.getVehicleNo()) ? "0" : s.getVehicleNo());
            data.setSpeed(StringUtils.isBlank(s.getVehicleNo()) ? "0" : s.getSpeed() + "");
            data.setElectricCity(StringUtils.isBlank(s.getElectricity()) ? "0.0" : s.getElectricity());
            data.setOnlineStatus(ObjectUtils.isEmpty(s.getOnlineStatus()) ? 0 : s.getOnlineStatus());
            data.setTravelStatus(ObjectUtils.isEmpty(s.getTravelStatus()) ? 0 : s.getTravelStatus());
            data.setDoorStatus(ObjectUtils.isEmpty(s.getDoorStatus()) ? 1 : s.getDoorStatus());
            data.setControlMode(ObjectUtils.isEmpty(s.getControlMode()) ? 3 : s.getControlMode());
            data.setLat(StringUtils.isBlank(s.getLat()) ? "0" : s.getLat());
            data.setLng(StringUtils.isBlank(s.getLng()) ? "0" : s.getLng());

            data.setOdometer(s.getOdometer());
            data.setChargeStatus(s.getChargeStatus());
            data.setChargeGunStatus(s.getChargeGunStatus());
            data.setBatteryHealth(s.getBatteryHealth());
            data.setTplf(s.getTplf());
            data.setTplb(s.getTplb());
            data.setTprf(s.getTprf());
            data.setTprb(s.getTprb());

            data.setVehicleErrorCode(ObjectUtils.isEmpty(s.getVehicleErrorCode()) ? 0 : s.getVehicleErrorCode());
            data.setVehicleErrorMsg(s.getVehicleErrorMsg());
            data.setDataTime(now);
            data.setPullTime(now);
            return data;
        }).collect(Collectors.toList());
        return rawData;
    }
}
