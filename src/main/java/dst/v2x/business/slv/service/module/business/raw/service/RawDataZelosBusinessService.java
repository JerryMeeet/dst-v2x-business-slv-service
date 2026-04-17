package dst.v2x.business.slv.service.module.business.raw.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.dst.steed.vds.common.domain.response.PageDTO;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.enums.archive.VehicleOnlineStatusEnum;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.service.ZelosAutoApiService;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.VehicleInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 上报数据-九识相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Service
@Slf4j
public class RawDataZelosBusinessService {
    @Autowired
    private RawDataBusinessService rawDataBusinessService;
    @Resource
    private VehicleInfoServiceImpl vehicleInfoService;
    @Autowired
    private ZelosAutoApiService zelosAutoApiService;

    /**
     * 获取九识无人车的实时数据
     *
     * @author 江民来
     * @date 2025/2/12 14:01
     */
    public void getZelosDataInfo() {
        try {
            log.info("开始获取九识无人车的实时数据");
            // 创建查询对象
            VehicleInfoPageQueryDTO pageDTO = new VehicleInfoPageQueryDTO();
            pageDTO.setBelongCompany(VehicleCompanyEnum.VEHICLE_COMPANY_2.getCode());
            //初始化页码
            int pageNo = 1;
            //初始化总页数
            long totalPage = 0;
            //设置每页查询数量，每页最大100条
            pageDTO.setPageSize(100);

            //按照页码循环查询
            while (true) {
                pageDTO.setPageNum(pageNo);
                //分页请求九识获取车辆信息
                PageDTO<String> pageRes = vehicleInfoService.queryNoPage(pageDTO);

                // 如果是第一页，记录总页数
                if (pageNo == 1) {
                    totalPage = pageRes.getTotalPage();
                    log.info("总页数：{}", totalPage);
                }

                // 输出当前查询页码
                log.info("当前请求页数：{}", pageNo);

                //从九识获取车辆动态数据
                getZelosDataInfo(pageRes.getList());

                //增加页码
                pageNo++;

                // 如果超过总页数，结束循环
                if (pageNo > totalPage) {
                    break;
                }
            }
            log.info("九识无人车实时数据请求结束");
        } catch (Exception e) {
            log.error("请求九识无人车的实时数据异常", e);
        }
    }

    /**
     * 获取九识无人车的实时数据
     */
    private void getZelosDataInfo(List<String> vehicleNos){
        if(CollectionUtils.isEmpty(vehicleNos)){
            return;
        }
        List<RawData> datas = zelosAutoApiService.getAutoVehiclertInfo(vehicleNos);
        log.info("获取九识无人车的实时数据,{}条", CollectionUtils.isEmpty(datas)?0:datas.size());

        //保存数据库，只保存开机状态的
        rawDataBusinessService.insertToDB(datas.stream()
                .filter(d->d.getOnlineStatus()!= VehicleOnlineStatusEnum.OFF_LINE.getCode())
                .collect(Collectors.toList()));

        //入redis
        rawDataBusinessService.flushRealtimeDataRedis(datas);
    }
}
