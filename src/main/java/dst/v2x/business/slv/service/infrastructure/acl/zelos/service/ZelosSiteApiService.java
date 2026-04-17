package dst.v2x.business.slv.service.infrastructure.acl.zelos.service;

import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.utils.LangUtil;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.api.ZelosAutoCarFeignApi;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.request.ZelosGetSiteInfoReq;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosResponse2;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosSiteInfoRes;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.StationInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 九识数据相关接口-站点
 */
@Slf4j
@Service
public class ZelosSiteApiService {
    @Resource
    private ZelosAutoCarFeignApi zelosAutoCarFeignApi;

    /**
     * 根据站点编号查询站点信息
     * @param siteNos
     * @return
     */
    public List<StationInfo> getSiteInfos(List<String> siteNos){
        ZelosGetSiteInfoReq req = new ZelosGetSiteInfoReq();
        req.setSiteNos(siteNos);
        ZelosResponse2<List<ZelosSiteInfoRes>> vehicleInfoResponse = zelosAutoCarFeignApi.getSiteInfos(req);
        if(vehicleInfoResponse == null){
            return new ArrayList<>();
        }
        return convertZelosStationInfoRes(vehicleInfoResponse.getData());
    }

    /**
     * 九识站点信息转换为数据库对象
     */
    private List<StationInfo> convertZelosStationInfoRes(List<ZelosSiteInfoRes> zelosSiteInfos){
        if(CollectionUtils.isEmpty(zelosSiteInfos)){
            return new ArrayList<>();
        }
        return zelosSiteInfos.stream().map(d->{
            StationInfo stationInfo = new StationInfo();
            stationInfo.setStationNo(d.getSiteNo());
            stationInfo.setStationName(d.getSiteName());
            stationInfo.setStationAddress(d.getSiteAddress());
            stationInfo.setBelongCompany(VehicleCompanyEnum.VEHICLE_COMPANY_2.getCode());
            stationInfo.setLat(LangUtil.parseDouble(d.getLat()));
            stationInfo.setLng(LangUtil.parseDouble(d.getLng()));
            return stationInfo;
        }).collect(Collectors.toList());
    }
}
