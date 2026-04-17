package dst.v2x.business.slv.service.module.business.archive.service;

import cn.hutool.core.collection.CollUtil;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.api.WhiteRhinoRemoteApi;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.request.WhiteRhinoVehicePageReq;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.response.WhiteRhinoVehicleInfoRes;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.response.WhiteRhinoVehiclePageRes;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.service.WhiteRhinoRemoteApiService;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.VehicleInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 档案信息-车辆信息-白犀牛相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Service
@Slf4j
public class VehicleInfoWhiteRhinoBusinessService {
    @Autowired
    private VehicleInfoServiceImpl vehicleInfoService;
    @Resource
    private WhiteRhinoRemoteApi whiteRhinoRemoteApi;
    @Autowired
    private WhiteRhinoRemoteApiService whiteRhinoRemoteApiService;

    /**
     * 拉取白犀牛最新的车辆入库及更新缓存
     */
    public void updateWhiteRhinoVehicleInfo() {
        try {
            log.info("开始拉取白犀牛最新车辆入库及更新缓存");
            // 创建查询对象
            WhiteRhinoVehicePageReq pageDTO = new WhiteRhinoVehicePageReq();
            //初始化页码
            int pageNo = 1;
            //初始化总页数
            long totalPage = 0;
            //设置每页查询数量
            pageDTO.setPageSize(1000);

            //按照页码循环查询
            while (true) {
                pageDTO.setPageNo(pageNo);
                //分页请求白犀牛获取车辆信息
                Response<WhiteRhinoVehiclePageRes> response = whiteRhinoRemoteApi.getVehicleList(pageDTO);
                if (response == null || response.getData() == null) {
                    return;
                }

                List<String> vehicleNoList = response.getData().getVehicles();
                if (CollUtil.isEmpty(vehicleNoList)) {
                    return;
                }

                // 如果是第一页，记录总页数
                if (pageNo == 1) {
                    totalPage = response.getData().getTotalPage();
                    log.info("总页数：{}", totalPage);
                }

                // 输出当前查询页码
                log.info("当前请求页数：{}", pageNo);

                // 批量入库
                vehicleInfoService.batchAddOrUpdate(getVehicleInfoByNo(vehicleNoList));

                //增加页码
                pageNo++;

                // 如果超过总页数，结束循环
                if (pageNo > totalPage) {
                    break;
                }
            }
            log.info("结束拉取白犀牛最新车辆入库及更新缓存");
        } catch (Exception e) {
            log.error("拉取白犀牛最新车辆入库及更新缓存异常", e);
        }
    }

    /**
     * 根据车牌号获取车辆信息
     * @param vehicleNoList
     */
    private List<VehicleInfo> getVehicleInfoByNo(List<String> vehicleNoList){
        //根据车牌获取车辆信息
        Map<String, WhiteRhinoVehicleInfoRes> vehicleInfoMap = whiteRhinoRemoteApiService.getVehicleInfoMap(vehicleNoList);

        //将白犀牛车辆信息转换为数据车辆信息对象
        return vehicleNoList.stream().map(d->
                convertWhiteRhinoVehicleInfoRes(d, vehicleInfoMap.get(d))
        ).collect(Collectors.toUnmodifiableList());
    }

    /**
     * 白犀牛车辆信息转换为数据车辆信息对象
     */
    private VehicleInfo convertWhiteRhinoVehicleInfoRes(String vehicleNo, WhiteRhinoVehicleInfoRes whiteRhinoVehicleInfoRes){
        VehicleInfo vehicleInfo = new VehicleInfo();
        if(whiteRhinoVehicleInfoRes != null){
            BeanUtils.copyProperties(whiteRhinoVehicleInfoRes, vehicleInfo);
        }
        vehicleInfo.setVehicleNo(vehicleNo);
        vehicleInfo.setBelongCompany(VehicleCompanyEnum.VEHICLE_COMPANY_1.getCode());
        return vehicleInfo;
    }
}
