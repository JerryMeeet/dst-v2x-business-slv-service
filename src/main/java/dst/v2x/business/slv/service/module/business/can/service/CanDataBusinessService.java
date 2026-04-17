package dst.v2x.business.slv.service.module.business.can.service;

import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.common.redis.RedisKeyConstant;
import dst.v2x.business.slv.service.common.utils.DstRedisUtil;
import dst.v2x.business.slv.service.common.utils.RedisUtil;
import dst.v2x.business.slv.service.common.utils.StatusDescUtil;
import dst.v2x.business.slv.service.infrastructure.doris.dto.CanPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.entity.CanData;
import dst.v2x.business.slv.service.infrastructure.doris.service.CanDataServiceImpl;
import dst.v2x.business.slv.service.infrastructure.doris.vo.CanDataVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * CAN数据-相关服务
 *
 * @author: pengyunlin
 * @date: 2025/8/13 15:51
 */
@Service
@Slf4j
public class CanDataBusinessService {
    @Resource
    private CanDataServiceImpl canDataService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private CanDataNavBusinessService canDataNavBusinessService;

    /**
     * 查询车辆最新一条数据
     */
    public CanDataVO queryLatest(String vehicleNo) {
        Object obj = redisUtil.hget(DstRedisUtil.toKey(RedisKeyConstant.VEHICLE_REALTIME_CAN_DATA), vehicleNo);
        if (obj == null) {
            return null;
        }

        CanDataVO vo = DstJsonUtil.toObject(obj.toString(), CanDataVO.class);
        // 状态值转换
        convertStatusDesc(vo);
        return vo;
    }

    /**
     * 查询无人车CAN历史数据(分页)
     *
     * @param queryDTO
     * @return
     */
    public PageDTO<CanDataVO> queryPage(CanPageQueryDTO queryDTO) {
        PageDTO<CanDataVO> pageDTO = canDataService.queryPage(queryDTO);

        //翻译状态值
        pageDTO.getList().forEach(vo->convertStatusDesc(vo));
        return pageDTO;
    }

    /**
     * 翻译状态值
     */
    private void convertStatusDesc(CanDataVO vo){
        StatusDescUtil.convertStatusDesc(vo.getBms());
        StatusDescUtil.convertStatusDesc(vo.getPdu());
        StatusDescUtil.convertStatusDesc(vo.getVcu());
    }

    /**
     * 保存Redis和数据库中
     */
    public void insertToDB(List<CanData> canDatas) {
        if(CollectionUtils.isEmpty(canDatas)){
            return;
        }
        // 实时数据入redis
        flushDataToRedis(canDatas);

        // 原始数据入库
        canDataService.insertBatch(canDatas);

        // 轨迹数据入库
        canDataNavBusinessService.insertBatch(canDatas);
    }

    /**
     * 动态数据刷到Redis
     */
    private void flushDataToRedis(List<CanData> canDatas) {
        if(CollectionUtils.isEmpty(canDatas)){
            return;
        }
        // list转map，key为车辆编号，value为数据，同一台车取最新一条数据
        Map<String, CanData> map = canDatas.stream()
                .collect(Collectors.toMap(
                        CanData::getVehicleNo,
                        Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparing(CanData::getDataTime))
                ));

        // 刷新缓存
        map.forEach((hashField, canData) -> redisUtil.hset(DstRedisUtil.toKey(RedisKeyConstant.VEHICLE_REALTIME_CAN_DATA), hashField, DstJsonUtil.toString(canData)));
    }
}
