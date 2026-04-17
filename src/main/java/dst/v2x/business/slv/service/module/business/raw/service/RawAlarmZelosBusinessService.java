package dst.v2x.business.slv.service.module.business.raw.service;

import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.enums.raw.RawAlarmTypeEnum;
import dst.v2x.business.slv.service.common.utils.CommonUtils;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.api.ZelosAutoCarFeignApi;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.request.ZelosAlarmReq;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosAlarmRes;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosResponse2;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.VehicleInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawAlarmDay;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawAlarmHistory;
import dst.v2x.business.slv.service.infrastructure.doris.mapper.RawAlarmDayMapper;
import dst.v2x.business.slv.service.infrastructure.doris.mapper.RawAlarmHistoryMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 报警数据-九识无人车相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Service
@Slf4j
public class RawAlarmZelosBusinessService {
    @Resource
    private VehicleInfoServiceImpl vehicleInfoService;
    @Resource
    private ZelosAutoCarFeignApi zelosAutoCarFeignApi;
    @Autowired
    private RawAlarmDayMapper rawAlarmDayMapper;
    @Autowired
    private RawAlarmHistoryMapper rawAlarmHistoryMapper;

    /**
     * 获取九识报警数据
     *
     * @author 江民来
     * @date 2025/6/12 16:15
     */
    public void getZelosVehicleAlarmDataInfo() {
        // 创建查询对象
        VehicleInfoPageQueryDTO pageDTO = new VehicleInfoPageQueryDTO();
        // 查询九识的车辆编号
        pageDTO.setBelongCompany(VehicleCompanyEnum.VEHICLE_COMPANY_2.getCode());
        //初始化页码
        int pageNo = 1;
        //初始化总页数
        long totalPage = 0;
        //设置每页查询数量
        pageDTO.setPageSize(100);
        //按照页码循环查询
        while (true) {
            pageDTO.setPageNum(pageNo);
            //分页
            PageDTO<String> pageRes = vehicleInfoService.queryNoPage(pageDTO);
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
            log.info("拉取九识报警数据，当前请求页数：{},总页数：{}", pageNo, totalPage);
            // 拿到车辆编号
            List<String> vehicleVinList = pageRes.getList();
            // 如果查不到 直接中止
            if (CollectionUtils.isEmpty(vehicleVinList)) {
                return;
            }
            // 创建对象，请求九识的接口
            ZelosAlarmReq req = new ZelosAlarmReq();
            req.setVehicleNoIds(vehicleVinList);
            req.setAlarmmode(2);
            ZelosResponse2<List<ZelosAlarmRes>> res = zelosAutoCarFeignApi.getZelosAlarmInfo(req);
            if (res == null || CollectionUtils.isEmpty(res.getData())) {
                if (pageNo < totalPage) {
                    pageNo++;
                    continue;
                } else {
                    break;
                }
            }
            log.info("拉取九识无人车的告警数据，响应：{}", DstJsonUtil.toString(res));
            List<ZelosAlarmRes> resList = res.getData();
            // 存储到数据库
            this.getRealTimeDataFromZelos(resList);
            this.getRawAlarmHistoryFromZelos(resList);
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
     * @param res
     * @return RawAlarmRealtime
     * @author 江民来
     * @date 2025/6/12 18:20
     */
    public void getRealTimeDataFromZelos(List<ZelosAlarmRes> res) {
        if (CollectionUtils.isEmpty(res)) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        List<RawAlarmDay> saveList = new ArrayList<>();
        for (ZelosAlarmRes zel : res) {
            RawAlarmDay alarmDay = new RawAlarmDay();
            alarmDay.setVehicleNo(zel.getVehicleNo());
            alarmDay.setDataTime(now);
            // 目前九识的报警枚举编号和我们的是相同的 ，RawAlarmTypeEnum枚举类从1-33都是参照九识的，以后如果还有其他的，可能就得改逻辑了
            alarmDay.setDataType(RawAlarmTypeEnum.getCodeByName(zel.getAlarmType()));
            alarmDay.setEmergencyStopTime(CommonUtils.parseToLocalDateTime(zel.getEmergencyStopTime()));
            alarmDay.setDoneTime(CommonUtils.parseToLocalDateTime(zel.getDoneTime()));
            saveList.add(alarmDay);
        }
        // 保存到实时当天的数据库
        rawAlarmDayMapper.insertBatch(saveList);

    }

    /**
     * 数据组装成数据库保存的对象(历史数据)
     *
     * @param res
     * @return RawAlarmHistory
     * @author 江民来
     * @date 2025/6/12 18:20
     */
    public void getRawAlarmHistoryFromZelos(List<ZelosAlarmRes> res) {
        if (CollectionUtils.isEmpty(res)) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        List<RawAlarmHistory> saveList = new ArrayList<>();
        for (ZelosAlarmRes zel : res) {
            RawAlarmHistory history = new RawAlarmHistory();
            history.setVehicleNo(zel.getVehicleNo());
            history.setDataTime(now);
            // 目前九识的报警枚举编号和我们的是相同的 ，RawAlarmTypeEnum枚举类从1-33都是参照九识的，以后如果还有其他的，可能就得改逻辑了
            history.setDataType(RawAlarmTypeEnum.getCodeByName(zel.getAlarmType()));
            history.setEmergencyStopTime(CommonUtils.parseToLocalDateTime(zel.getEmergencyStopTime()));
            history.setDoneTime(CommonUtils.parseToLocalDateTime(zel.getDoneTime()));
            saveList.add(history);
        }
        rawAlarmHistoryMapper.insertBatch(saveList);
    }
}
