package dst.v2x.business.slv.service.module.business.raw.service;

import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.enums.raw.RawAlarmTypeEnum;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.api.NeolixRemoteApi;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.enmus.NeolixCodeBridgeEnum;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.request.NeolixVehicleInfoReq;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixResponse;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.VehicleFaultRes;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.VehicleInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.VehicleInfoPageQueryVO;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawAlarmDay;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawAlarmHistory;
import dst.v2x.business.slv.service.infrastructure.doris.service.RawAlarmDayServiceImpl;
import dst.v2x.business.slv.service.infrastructure.doris.service.RawAlarmHistoryServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 报警数据-新石器相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Service
@Slf4j
public class RawAlarmNeolixBusinessService {
    @Resource
    private VehicleInfoServiceImpl vehicleInfoService;
    @Resource
    private NeolixRemoteApi neolixRemoteApi;
    @Autowired
    private RawAlarmDayServiceImpl rawAlarmDayService;
    @Autowired
    private RawAlarmHistoryServiceImpl rawAlarmHistoryService;

    /**
     * 获取新石器报警数据
     *
     * @author 江民来
     * @date 2025/6/12 16:15
     */
    public void getNeolixVehicleAlarmDataInfo() {
        // 创建查询对象
        VehicleInfoPageQueryDTO pageDTO = new VehicleInfoPageQueryDTO();
        pageDTO.setBelongCompany(VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode());
        //初始化页码
        int pageNo = 1;
        //初始化总页数
        long totalPage = 0;
        //设置每页查询数量
        pageDTO.setPageSize(200);

        //按照页码循环查询
        while (true) {
            pageDTO.setPageNum(pageNo);
            //分页请求获取车辆信息
            PageDTO<VehicleInfoPageQueryVO> pageRes = vehicleInfoService.queryPage(pageDTO);
            // 如果是第一页，记录总页数
            if (pageNo == 1) {
                totalPage = pageRes.getTotalPage();
                log.info("总页数：{}", totalPage);
                if (totalPage <= 0) {
                    log.warn("分页查询总页数为0，提前结束！");
                    return;
                }
            }

            // 输出当前查询页码
            log.info("拉取新石器报警数据，当前请求页数：{},总页数：{}", pageNo, totalPage);
            List<VehicleInfoPageQueryVO> vehicleList = pageRes.getList();
            List<RawAlarmDay> alarmDayList = new ArrayList<>();
            List<RawAlarmHistory> historyDataList = new ArrayList<>();
            if (CollectionUtils.isEmpty(vehicleList)) {
                log.info("未查到新石器车辆信息");
                return;
            }
            // 循环拉取车辆上报数据
            for (VehicleInfoPageQueryVO vehicle : vehicleList) {
                NeolixVehicleInfoReq neolixVehicleInfoDTO = new NeolixVehicleInfoReq();
                neolixVehicleInfoDTO.setVin(vehicle.getVehicleVin());
                NeolixResponse<VehicleFaultRes> res = neolixRemoteApi.getVehicleFault(neolixVehicleInfoDTO);
                log.info("拉取新石器无人车的告警数据，响应：{}", DstJsonUtil.toString(res));
                if (res == null || res.getData() == null) {
                    continue;
                }
                // 转换成保存数据的属性类
                RawAlarmDay realTimeDataFromNeolix = getRealTimeDataFromNeolix(res, vehicle.getVehicleNo());
                RawAlarmHistory historyData = getRawAlarmHistoryFromNeolix(res, vehicle.getVehicleNo());
                // add到list
                alarmDayList.add(realTimeDataFromNeolix);
                historyDataList.add(historyData);
            }
            if (CollectionUtils.isNotEmpty(alarmDayList)) {
                // 保存到实时当天的数据库
                rawAlarmDayService.insertBatch(alarmDayList);
            }
            if (CollectionUtils.isNotEmpty(historyDataList)) {
                // 保存历史数据到数据库
                rawAlarmHistoryService.insertBatch(historyDataList);
            }
            //增加页码
            pageNo++;
            // 如果超过总页数，结束循环
            if (pageNo > totalPage) {
                break;
            }
        }
    }

    /**
     * 数据组装成数据库保存的对象(实时数据)
     *
     * @param response
     * @return RawAlarmRealtime
     * @author 江民来
     * @date 2025/6/12 18:20
     */
    private RawAlarmDay getRealTimeDataFromNeolix(NeolixResponse<VehicleFaultRes> response, String vehicleNo) {
        LocalDateTime now = LocalDateTime.now();
        VehicleFaultRes vehiclefault = response.getData();
        RawAlarmDay vehicleAlarm = new RawAlarmDay();
        vehicleAlarm.setVehicleNo(vehicleNo);
        // 根据新石器的报警编号转换成我们自己的报警编号
        RawAlarmTypeEnum rawAlarmTypeEnum = NeolixCodeBridgeEnum.getAlarmType(vehiclefault.getFaultCode());
        vehicleAlarm.setDataType(rawAlarmTypeEnum.getCode());
        vehicleAlarm.setDataTime(now);
        return vehicleAlarm;
    }

    /**
     * 数据组装成数据库保存的对象(历史数据)
     *
     * @param response
     * @return RawAlarmHistory
     * @author 江民来
     * @date 2025/6/12 18:20
     */
    private RawAlarmHistory getRawAlarmHistoryFromNeolix(NeolixResponse<VehicleFaultRes> response, String vehicleNo) {
        LocalDateTime now = LocalDateTime.now();
        VehicleFaultRes vehiclefault = response.getData();
        RawAlarmHistory vehicleAlarm = new RawAlarmHistory();
        vehicleAlarm.setVehicleNo(vehicleNo);
        // 根据新石器的报警编号转换成我们自己的报警编号
        RawAlarmTypeEnum rawAlarmTypeEnum = NeolixCodeBridgeEnum.getAlarmType(vehiclefault.getFaultCode());
        vehicleAlarm.setDataType(rawAlarmTypeEnum.getCode());
        vehicleAlarm.setDataTime(now);
        return vehicleAlarm;
    }

}
