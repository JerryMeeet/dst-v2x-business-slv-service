package dst.v2x.business.slv.service.module.business.can.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.dst.steed.vds.common.domain.response.PageDTO;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.service.ZelosCanApiService;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.VehicleInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.doris.entity.CanData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CAN数据-九识相关服务
 *
 * @author: pengyunlin
 * @date: 2025/8/13 15:51
 */
@Service
@Slf4j
public class CanDataZelosBusinessService {
    @Autowired
    private CanDataBusinessService rawDataBusinessService;
    @Resource
    private VehicleInfoServiceImpl vehicleInfoService;
    @Autowired
    private ZelosCanApiService zelosCanApiService;

    /**
     * 获取九识无人车的CAN数据
     */
    public void getZelosCanData() {
        try {
            log.info("开始获取九识无人车的CAN数据");
            // 创建查询对象
            VehicleInfoPageQueryDTO pageDTO = new VehicleInfoPageQueryDTO();
            pageDTO.setBelongCompany(VehicleCompanyEnum.VEHICLE_COMPANY_2.getCode());
            //初始化页码
            int pageNo = 1;
            //初始化总页数
            long totalPage = 0;
            //设置每页查询数量，每页最大100条
            pageDTO.setPageSize(10);

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

                //从九识获取车辆CAN数据
                getZelosCanData(pageRes.getList());

                //增加页码
                pageNo++;

                // 如果超过总页数，结束循环
                if (pageNo > totalPage) {
                    break;
                }
            }
            log.info("九识无人车CAN数据请求结束");
        } catch (Exception e) {
            log.error("请求九识无人车的CAN数据异常", e);
        }
    }

    /**
     * 获取九识无人车的CAN数据
     */
    public void getZelosCanData(List<String> vehicleNos){
        if(CollectionUtils.isEmpty(vehicleNos)){
            return;
        }
        List<CanData> datas = zelosCanApiService.getZelosCanData(vehicleNos);
        log.info("获取九识无人车的CAN数据,{}条", CollectionUtils.isEmpty(datas)?0:datas.size());

        //保存数据
        rawDataBusinessService.insertToDB(datas);
    }
}
