package dst.v2x.business.slv.service.module.business.archive.service;

import com.dst.steed.vds.common.domain.response.PageDTO;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.infrastructure.acl.iotda.service.IotdaRemoteApiService;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.VehicleInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 档案信息-车辆信息-Iotda相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/27 15:51
 */
@Service
@Slf4j
public class VehicleInfoIotdaBusinessService {
    @Autowired
    private VehicleInfoServiceImpl vehicleInfoService;
    @Autowired
    private IotdaRemoteApiService iotdaRemoteApiService;

    /**
     * 同步设备信息至iotda
     */
    public void syncDeviceInfoToIotda(){
        try {
            for (VehicleCompanyEnum value : VehicleCompanyEnum.values()) {
                log.info("开始同步设备信息至iotda，车辆所属公司：{}", value.getDesc());
                int num = syncDeviceInfoToIotda(value);
                log.info("结束同步设备信息至iotda，车辆所属公司：{}，新增设备数量：{}", value.getDesc(), num);
            }
        }catch (Exception e){
            log.error("同步设备信息至iotda异常！", e);
        }
    }

    /**
     * 同步设备信息至iotda，从数据库中查询车辆编号同步至华为云iotda
     * @param companyEnum 车辆所属公司枚举
     */
    private int syncDeviceInfoToIotda(VehicleCompanyEnum companyEnum) {
        // 创建查询对象
        VehicleInfoPageQueryDTO pageDTO = new VehicleInfoPageQueryDTO();
        // 查询iotda中没有的设备
        pageDTO.setIotdaDeviceIdIsNull(true);
        pageDTO.setBelongCompany(companyEnum.getCode());
        //初始化页码
        int pageNo = 1;
        //初始化总页数
        long totalPage = 0;
        //设置每页查询数量
        pageDTO.setPageSize(100);
        //新增设备数量
        int num = 0;
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
                    return 0;
                }
            }
            // 输出当前查询页码
            log.info("同步设备信息至iotda，当前请求页数：{},总页数：{}", pageNo, totalPage);
            // 拿到车辆编号
            List<String> vehicleNos = pageRes.getList();
            // 如果查不到 直接中止
            if (CollectionUtils.isEmpty(vehicleNos)) {
                return 0;
            }

            num += vehicleNos.size();
            //循环新增设备至iotda
            vehicleNos.forEach(vehicleNo->{
                //调用华为云iotda新增设备
                String iotdaDeviceId = iotdaRemoteApiService.addDevice(vehicleNo, companyEnum);

                //将iotda设备ID保存至数据库
                vehicleInfoService.updateIotdaDeviceId(iotdaDeviceId, vehicleNo);
            });

            pageNo++;
            // 如果超过总页数，结束循环
            if (pageNo > totalPage) {
                break;
            }
        }
        return num;
    }
}
