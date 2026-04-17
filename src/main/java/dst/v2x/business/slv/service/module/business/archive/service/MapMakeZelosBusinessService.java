package dst.v2x.business.slv.service.module.business.archive.service;

import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.service.ZelosMapMakeApiService;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.MapMake;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.MapMakeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 档案信息-路线-九识相关服务
 *
 * @author: pengyunlin
 * @date: 2025/8/18 15:51
 */
@Service
@Slf4j
public class MapMakeZelosBusinessService {
    @Autowired
    private MapMakeServiceImpl mapMakeService;
    @Autowired
    private ZelosMapMakeApiService zelosMapMakeApiService;

    /**
     * 拉取九识路线
     */
    public void updateZelosMapMake() {
        try {
            log.info("开始拉取九识路线");
            List<MapMake> zelosMapMakeList = zelosMapMakeApiService.getZelosMapMakeList();
            mapMakeService.batchAddOrUpdate(zelosMapMakeList, VehicleCompanyEnum.VEHICLE_COMPANY_2.getCode());
            log.info("结束拉取九识路线");
        } catch (Exception e) {
            log.error("拉取九识路线异常", e);
        }
    }
}
