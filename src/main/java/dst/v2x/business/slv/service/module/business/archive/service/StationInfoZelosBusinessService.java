package dst.v2x.business.slv.service.module.business.archive.service;

import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.api.ZelosAutoCarFeignApi;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.request.ZelosGetVehicleListReq;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosPageRes;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosResponse2;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.service.ZelosSiteApiService;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.StationInfoServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 档案信息-站点信息-九识相关服务
 *
 * @author: pengyunlin
 * @date: 2025/8/11 15:51
 */
@Service
@Slf4j
public class StationInfoZelosBusinessService {
    @Autowired
    private StationInfoServiceImpl stationInfoService;
    @Resource
    private ZelosAutoCarFeignApi zelosAutoCarFeignApi;
    @Autowired
    private ZelosSiteApiService zelosSiteApiService;

    /**
     * 拉取九识站点信息
     */
    public void updateZelosStationInfo() {
        try {
            log.info("开始拉取九识站点信息");
            // 初始页码
            Integer pageNo = 1;
            //初始化总页数
            long totalPage = 0;
            // 每次最大100条
            ZelosGetVehicleListReq req = new ZelosGetVehicleListReq();
            req.setPageSize(100);
            while (true) {
                req.setPageNo(pageNo);
                // 调用九识的接口获取站点数据-站点编号
                ZelosResponse2<ZelosPageRes> response = zelosAutoCarFeignApi.getZelosSitePage(req);
                if (response == null || response.getData() == null) {
                    return;
                }
                ZelosPageRes page = response.getData();
                // 如果是第一页，记录总页数
                if (pageNo == 1) {
                    totalPage = page.getTotalPages();
                    log.info("总页数：{}", totalPage);
                }

                // 输出当前查询页码
                log.info("当前请求页数：{}", pageNo);

                //通过站点编号获取站点信息并入库
                stationInfoService.batchAddOrUpdate(zelosSiteApiService.getSiteInfos(page.getSiteNos()), VehicleCompanyEnum.VEHICLE_COMPANY_2.getCode());

                // 页码+1
                pageNo++;

                // 如果超过总页数，结束循环
                if (pageNo > totalPage) {
                    break;
                }
            }
            log.info("结束拉取九识站点信息");
        } catch (Exception e) {
            log.error("拉取九识站点信息异常", e);
        }
    }
}
