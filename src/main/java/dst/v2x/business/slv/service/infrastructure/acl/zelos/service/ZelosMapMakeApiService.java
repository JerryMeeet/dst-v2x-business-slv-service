package dst.v2x.business.slv.service.infrastructure.acl.zelos.service;

import dst.v2x.business.slv.service.common.enums.archive.MapMakeStatusEnum;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.api.ZelosAutoCarFeignApi;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosMapMakeRes;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosResponse;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosTotalListRes;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.MapMake;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 九识Can数据相关接口-路线
 */
@Slf4j
@Service
public class ZelosMapMakeApiService {
    @Resource
    private ZelosAutoCarFeignApi zelosAutoCarFeignApi;

    /**
     * 获取路线列表
     * @return
     */
    public List<MapMake> getZelosMapMakeList(){
        ZelosResponse<ZelosTotalListRes<ZelosMapMakeRes>> response = zelosAutoCarFeignApi.getZelosMapMakeList();
        if(response == null){
            return new ArrayList<>();
        }
        ZelosTotalListRes<ZelosMapMakeRes> data = response.getData();
        if(data == null){
            return new ArrayList<>();
        }
        return convertZelosMapMakeRes(data.getList());
    }
    
    /**
     * 九识路线转换为数据库对象
     */
    private List<MapMake> convertZelosMapMakeRes(List<ZelosMapMakeRes> zelosMapMakes){
        if(CollectionUtils.isEmpty(zelosMapMakes)){
            return new ArrayList<>();
        }
        return zelosMapMakes.stream().map(d->{
            MapMake mapMake = new MapMake();
            mapMake.setMapMakeId(d.getId());
            mapMake.setMapMakeName(d.getName());
            mapMake.setProvinceName(d.getProvince());
            mapMake.setCityName(d.getCity());
            mapMake.setDistrictName(d.getDistrict());
            mapMake.setStatus(convertZelosMapMakeStatus(d.getStatus()));
            mapMake.setBelongCompany(VehicleCompanyEnum.VEHICLE_COMPANY_2.getCode());
            return mapMake;
        }).collect(Collectors.toList());
    }

    /**
     * 路线状态转换,将九识路线状态转换为数据库路线状态
     */
    private Integer convertZelosMapMakeStatus(String status){
        if(status == null){
            return null;
        }
        switch (status){
         case "完成交付":
             return MapMakeStatusEnum.STATUS_1.getCode();
         case "已完成交付":
             return MapMakeStatusEnum.STATUS_1.getCode();
         case "新建":
             return MapMakeStatusEnum.STATUS_2.getCode();
         case "测试中":
             return MapMakeStatusEnum.STATUS_3.getCode();
         case "采图中":
             return MapMakeStatusEnum.STATUS_4.getCode();
         default:
             return null;
        }
    }
}
