package dst.v2x.business.slv.service.module.business.archive.service;

import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.service.NeolixRemoteApiService;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.VehicleInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.StationInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.StationInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 档案信息-站点信息-新石器相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Service
@Slf4j
public class StationInfoNeolixBusinessService {
    @Autowired
    private VehicleInfoServiceImpl vehicleInfoService;
    @Autowired
    private StationInfoServiceImpl stationInfoService;
    @Autowired
    private NeolixRemoteApiService neolixRemoteApiService;

    /**
     * 获取站点信息-实时
     */
    public Response<List<StationInfo>> getStationInfosByRealtime(String vin) {
        if (StringUtils.isEmpty(vin)) {
            return Response.error("VIN不能为空");
        }
        return Response.succeed(neolixRemoteApiService.getStationList(vin));
    }

    /**
     * 拉取新石器无人车的站点信息
     */
    public void updateNeolixStationInfo() {
        try {
            log.info("开始拉取新石器无人车的站点信息");
            // 创建新石器车辆信息查询对象
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
                //分页请求新石器获取车辆信息
                PageDTO<String> pageRes = vehicleInfoService.queryVinPage(pageDTO);

                // 如果是第一页，记录总页数
                if (pageNo == 1) {
                    totalPage = pageRes.getTotalPage();
                    log.info("总页数：{}", totalPage);
                }

                // 输出当前查询页码
                log.info("当前请求页数：{}", pageNo);

                List<String> vehicleVinList = pageRes.getList();

                //根据vin获取站点信息
                vehicleVinList.forEach(vin->{
                    //调用新石器接口获取站点信息
                    List<StationInfo> stationList = neolixRemoteApiService.getStationList(vin);

                    //批量入库
                    stationInfoService.batchAddOrUpdate(stationList, VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode());
                });

                //增加页码
                pageNo++;

                // 如果超过总页数，结束循环
                if (pageNo > totalPage) {
                    break;
                }
            }
            log.info("结束拉取新石器无人车的站点信息");
        } catch (Exception e) {
            log.error("拉取新石器无人车的站点信息，发生异常!", e);
        }
    }
}
