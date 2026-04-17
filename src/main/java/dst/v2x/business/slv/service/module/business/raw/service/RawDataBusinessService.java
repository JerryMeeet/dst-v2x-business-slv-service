package dst.v2x.business.slv.service.module.business.raw.service;

import com.dst.steed.vds.common.constant.MinConstant;
import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.domain.response.Response;
import com.dst.steed.vds.common.util.DstJsonUtil;
import com.dst.steed.vds.common.util.DstThreadLocalUtil;
import dst.v2x.business.slv.service.common.redis.RedisKeyConstant;
import dst.v2x.business.slv.service.common.utils.DstRedisUtil;
import dst.v2x.business.slv.service.common.utils.RedisUtil;
import dst.v2x.business.slv.service.common.utils.StatusDescUtil;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawListQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawData;
import dst.v2x.business.slv.service.infrastructure.doris.service.RawDataServiceImpl;
import dst.v2x.business.slv.service.infrastructure.doris.vo.RawDataVO;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 上报数据-相关服务
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@Service
@Slf4j
public class RawDataBusinessService {
    @Autowired
    private RawDataServiceImpl rawDataService;
    @Autowired
    private RawDataNavBusinessService rawDataNavBusinessService;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 查询所有车辆最新一条数据
     *
     * @return
     */
    public List<RawDataVO> queryLatestList() {
        log.info(">>>查询所有车辆最新一条数据");

        Map<Object, Object> hmget = redisUtil.hmget(DstRedisUtil.toKey(RedisKeyConstant.VEHICLE_REALTIME_DATA));
        if (hmget.isEmpty()) {
            return Collections.emptyList();
        }

        List<RawDataVO> voList = new ArrayList<>();

        hmget.forEach((hashField, data) -> {
            RawDataVO vo = new RawDataVO();
            RawData rawData = DstJsonUtil.toObject(data.toString(), RawData.class);
            // 过滤无效的经纬度数据
            if(!isValidLatLon(rawData.getLat(), rawData.getLng())){
                return;
            }

            BeanUtils.copyProperties(rawData, vo);
            // 状态值转换
            StatusDescUtil.convertStatusDesc(vo);
            voList.add(vo);
        });
        return voList;
    }

    /**
     * 查询指定车辆最新一条数据
     *
     * @return
     */
    public RawDataVO queryLatest(String vehicleNo) {
        log.info(">>>查询指定车辆最新一条数据");

        Map<Object, Object> hmget = redisUtil.hmget(DstRedisUtil.toKey(RedisKeyConstant.VEHICLE_REALTIME_DATA));
        if (hmget.isEmpty()) {
            return null;
        }

        return hmget.values().stream()
                // 转换为RawData对象
                .map(data -> DstJsonUtil.toObject(data.toString(), RawData.class))
                // 过滤出匹配车牌号且经纬度有效的数据
                .filter(rawData -> vehicleNo.equals(rawData.getVehicleNo())
                        && isValidLatLon(rawData.getLat(), rawData.getLng()))
                // 取第一条匹配的数据
                .findFirst()
                // 转换为RawDataVO并处理状态
                .map(rawData -> {
                    RawDataVO vo = new RawDataVO();
                    BeanUtils.copyProperties(rawData, vo);
                    StatusDescUtil.convertStatusDesc(vo);
                    return vo;
                })
                // 如果没有匹配数据则返回null
                .orElse(null);
    }

    /**
     * 查询无人车历史数据(不分页)
     *
     * @param queryDTO
     * @return Response
     * @author 江民来
     * @date 2024/11/16 11:07
     */
    public List<RawDataVO> queryList(RawListQueryDTO queryDTO) {
        List<RawDataVO> voList = rawDataService.queryList(queryDTO);

        // 状态值转换
        voList.forEach(vo->StatusDescUtil.convertStatusDesc(vo));
        return voList;
    }

    /**
     * 查询无人车历史数据(分页)
     *
     * @param queryDTO
     * @return
     */
    public PageDTO<RawDataVO> queryPage(RawPageQueryDTO queryDTO) {
        PageDTO<RawDataVO> pageDTO = rawDataService.queryPage(queryDTO);

        //翻译状态值
        pageDTO.getList().forEach(vo->StatusDescUtil.convertStatusDesc(vo));
        return pageDTO;
    }

    /**
     * 保存Redis和pg数据库中
     * 不管是否开机关机，都会刷到Redis,但是db只会保存开机状态的车
     *
     * @param rawDatas
     * @author 江民来
     * @date 2024/11/18 15:32
     */
    public void insertToDB(List<RawData> rawDatas) {
        if(CollectionUtils.isEmpty(rawDatas)){
            return;
        }
        //过滤无效的经纬度数据及车辆编号为空的数据
        List<RawData> validRawDatas = rawDatas.stream().filter(d->
                StringUtils.isNotBlank(d.getVehicleNo()) && isValidLatLon(d.getLat(), d.getLng())
        ).collect(Collectors.toList());

        // 原始数据入库
        rawDataService.insertBatch(validRawDatas);

        // 轨迹数据入库
        rawDataNavBusinessService.insertBatch(validRawDatas);
    }

    /**
     * 动态数据刷到Redis
     */
    public void flushRealtimeDataRedis(List<RawData> rawDatas) {
        if(CollectionUtils.isEmpty(rawDatas)){
            return;
        }
        //过滤无效的经纬度数据
        List<RawData> validRawDatas = rawDatas.stream().filter(d->isValidLatLon(d.getLat(), d.getLng())).collect(Collectors.toList());

        // list转map，key为车辆编号，value为数据
        Map<String, RawData> map = validRawDatas.stream().collect(Collectors.toMap(RawData::getVehicleNo, Function.identity()));

        // 刷新缓存
        map.forEach((hashField, rawData) -> redisUtil.hset(DstRedisUtil.toKey(RedisKeyConstant.VEHICLE_REALTIME_DATA), hashField, DstJsonUtil.toString(rawData)));
    }

    /**
     * 是否有效的经纬度
     * 73.55 <= lng <= 135.083 and 3.517 <= lat <= 53.55
     */
    private boolean isValidLatLon(String lat, String lng) {
        if(StringUtils.isEmpty(lat) || StringUtils.isEmpty(lng)){
            return false;
        }
        try {
            double latDouble = Double.parseDouble(lat);
            double lngDouble = Double.parseDouble(lng);
            return latDouble >= 3.517 && latDouble <= 53.55 && lngDouble >= 73.55 && lngDouble <= 135.083;
        } catch (Exception e) {
            return false;
        }
    }
}
