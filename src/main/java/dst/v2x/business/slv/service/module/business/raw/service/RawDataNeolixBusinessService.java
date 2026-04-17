package dst.v2x.business.slv.service.module.business.raw.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.dst.steed.vds.common.domain.response.PageDTO;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.common.enums.archive.VehicleControlModeEnum;
import dst.v2x.business.slv.service.common.enums.raw.VehicleErrorStatusEnum;
import dst.v2x.business.slv.service.common.utils.StatusDescUtil;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.api.NeolixRemoteApi;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixResponse;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixVehicleInfoRes;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.VehicleInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.VehicleInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.VehicleInfoServiceImpl;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawData;
import dst.v2x.business.slv.service.infrastructure.doris.vo.RawDataVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 上报数据-新石器相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Service
@Slf4j
public class RawDataNeolixBusinessService {
    @Resource
    private NeolixRemoteApi neolixRemoteApi;
    @Autowired
    private RawDataBusinessService rawDataBusinessService;
    @Resource
    private VehicleInfoServiceImpl vehicleInfoService;

    /**
     * 拉取新石器无人车实时数据
     */
    public void getNeolixVehicleDataInfo() {
        try {
            log.info("拉取新石器无人车实时数据-开始");
            // 创建查询对象
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

                //根据vins获取实时数据
                List<RawData> realDataList = getRealTimeDatasFromNeolix(vehicleVinList);

                //保存数据库，只保存开机状态的
                rawDataBusinessService.insertToDB(realDataList.stream().filter(s -> s.getOnlineStatus() != 0).collect(Collectors.toList()));

                //入redis
                rawDataBusinessService.flushRealtimeDataRedis(realDataList);

                //增加页码
                pageNo++;

                // 如果超过总页数，结束循环
                if (pageNo > totalPage) {
                    break;
                }
            }
            log.info("拉取新石器无人车实时数据-结束");
        } catch (Exception e) {
            log.error("拉取新石器无人车实时数据-异常", e);
        }
    }

    /**
     * 根据vins获取实时数据
     * @param vehicleVinList
     * @return
     */
    private List<RawData> getRealTimeDatasFromNeolix(List<String> vehicleVinList){
        vehicleVinList = vehicleVinList.stream().filter(d -> StringUtils.isNoneBlank(d)).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(vehicleVinList)){
            return new  ArrayList<>();
        }

        NeolixResponse<List<NeolixVehicleInfoRes>> response = neolixRemoteApi.batchGetVehicleList(vehicleVinList);
        if (response == null || CollectionUtils.isEmpty(response.getData())) {
            return new ArrayList<>();
        }

        return response.getData().stream().map(d-> convertNeolixData(d)).collect(Collectors.toList());
    }

    /**
     * 根据vehicleNo获取实时数据
     * @param vehicleNo
     * @return
     */
    public RawDataVO querLatestRealTime(String vehicleNo) {
        log.info(">>>查询指定车辆最新一条实时数据");
        VehicleInfo vehicleInfo = vehicleInfoService.queryOne(vehicleNo);
        if (ObjectUtils.isEmpty(vehicleInfo) || ObjectUtils.isEmpty(vehicleInfo.getVehicleVin())) {
            return null;
        }
        List<String> vinList = new ArrayList<>();
        vinList.add(vehicleInfo.getVehicleVin());
        List<RawData> rawDataList = getRealTimeDatasFromNeolix(vinList);
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(rawDataList)) {
            return null;
        }

        RawData rawData = rawDataList.get(0);
        RawDataVO vo = new RawDataVO();
        BeanUtils.copyProperties(rawData, vo);
        StatusDescUtil.convertStatusDesc(vo);
        return vo;
    }

    /**
     * 将新石器返回的控制模式转换为数据库实体的控制模式
     */
    private Integer convertControlMode(Integer controlMode) {
        if(controlMode == null){
            return null;
        }

        /**
         * 新石器：
         * - 自动驾驶模式->自动驾驶
         * - 远程脱困模式->远程驾驶
         * - 近场遥控模式-> 遥控驾驶
         * - 缺省-> 停车中
         */
        if(controlMode == 2){
            return VehicleControlModeEnum.MODE4.getCode();
        }else if(controlMode == 3){
            return VehicleControlModeEnum.MODE2.getCode();
        }else if(controlMode == 0){
            return VehicleControlModeEnum.MODE3.getCode();
        }
        return null;
    }

    /**
     * 将新石器返回的故障状态转换为数据库实体的故障状态
     * @param errorStatus
     * @return
     */
    private Integer convertErrorStatus(Integer errorStatus){
        if(errorStatus == null){
            return null;
        }
        if(errorStatus == 0){
            VehicleErrorStatusEnum.ERROR1.getCode();
        }else if(errorStatus == 1){
            VehicleErrorStatusEnum.ERROR2.getCode();
        }
        return null;
    }

    /**
     * 将新石器返回的数据转换为数据库实体
     * @param vehicleInfoVO
     * @return
     */
    private RawData convertNeolixData(NeolixVehicleInfoRes vehicleInfoVO) {
        LocalDateTime now = LocalDateTime.now();

        RawData rawData = new RawData();
        rawData.setVehicleNo(vehicleInfoVO.getVinId());
        rawData.setSpeed(vehicleInfoVO.getSpeed());
        rawData.setElectricCity(Objects.isNull(vehicleInfoVO.getRealBattery())? "0" : vehicleInfoVO.getRealBattery());
        //控制模式
        rawData.setControlMode(convertControlMode(vehicleInfoVO.getControlMode()));
        rawData.setLat(vehicleInfoVO.getPosition().getLat());
        rawData.setLng(vehicleInfoVO.getPosition().getLon());

        //开关机状态，0 未上线，1 已上线
        rawData.setOnlineStatus(vehicleInfoVO.getOnlineStatus() != null && vehicleInfoVO.getOnlineStatus() ? 1 : 0);

        //车门状态
        rawData.setDoorStatus(vehicleInfoVO.getDoorStatus());

        //充电状态
        rawData.setChargeStatus(vehicleInfoVO.getChargeStatus());

        //充电枪状态
        rawData.setChargeGunStatus(vehicleInfoVO.getChargeGunStatus());

        //电池健康度
        rawData.setBatteryHealth(vehicleInfoVO.getBatteryHealth());

        //轮胎压
        rawData.setTplf(vehicleInfoVO.getTplf()!=null?vehicleInfoVO.getTplf().intValue():null);
        rawData.setTplb(vehicleInfoVO.getTplb()!=null?vehicleInfoVO.getTplb().intValue():null);
        rawData.setTprf(vehicleInfoVO.getTprf()!=null?vehicleInfoVO.getTprf().intValue():null);
        rawData.setTprb(vehicleInfoVO.getTprb()!=null?vehicleInfoVO.getTprb().intValue():null);

        //车辆故障状态
        rawData.setErrorStatus(convertErrorStatus(vehicleInfoVO.getErrorStatus()));

        // 以下给默认值
        rawData.setTravelStatus(1);
        rawData.setPullTime(now);
        rawData.setDataTime(now);
        return rawData;
    }
}
