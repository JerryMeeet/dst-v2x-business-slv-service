package dst.v2x.business.slv.service.infrastructure.doris.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawAlarmListQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawAlarmHistory;
import dst.v2x.business.slv.service.infrastructure.doris.mapper.RawAlarmHistoryMapper;
import dst.v2x.business.slv.service.infrastructure.doris.vo.RawAlarmVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DST
 * @description 针对表【slv_raw_alarm_history】的数据库操作Service实现
 * @createDate 2025-06-12 14:58:51
 */
@Service
public class RawAlarmHistoryServiceImpl extends ServiceImpl<RawAlarmHistoryMapper, RawAlarmHistory> {
    @Autowired
    private RawAlarmHistoryMapper rawAlarmHistoryMapper;

    /**
     * 列表查询
     */
    public List<RawAlarmVO> queryList(RawAlarmListQueryDTO queryDTO) {
        LambdaQueryWrapper<RawAlarmHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(RawAlarmHistory::getDataTime, queryDTO.getStartDataTime())
                .le(RawAlarmHistory::getDataTime, queryDTO.getEndDataTime())
                .eq(RawAlarmHistory::getVehicleNo, queryDTO.getVehicleNo())
                .eq(queryDTO.getDataType() != null, RawAlarmHistory::getDataType, queryDTO.getDataType())
                // 根据 isNextPage 决定正序或倒序
                .last("ORDER BY data_time " + queryDTO.getIsNextPage() + " LIMIT " + queryDTO.getLimit()); // 拼接中间给空格，不然会报错
        return BeanUtil.copyToList(list(queryWrapper), RawAlarmVO.class);
    }


    /**
     * 批量新增
     *
     * @author 江民来
     * @date 2025/6/13 15:21
     */
    public void insertBatch(List<RawAlarmHistory> list) {
        rawAlarmHistoryMapper.insertBatch(list);
    }
}




