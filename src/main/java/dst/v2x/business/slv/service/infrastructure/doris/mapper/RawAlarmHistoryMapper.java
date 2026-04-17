package dst.v2x.business.slv.service.infrastructure.doris.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawAlarmHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author DST
 * @description 针对表【slv_raw_alarm_history】的数据库操作Mapper
 * @createDate 2025-06-12 14:58:51
 * @Entity dst.v2x.business.slv.service.infrastructure.doris.entity.RawAlarmHistory
 */
public interface RawAlarmHistoryMapper extends BaseMapper<RawAlarmHistory> {

    /**
     * 批量插入
     *
     * @author 江民来
     * @date 2025/6/13 10:14
     */
    void insertBatch(@Param("list") List<RawAlarmHistory> list);

}




