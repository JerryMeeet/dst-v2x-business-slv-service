package dst.v2x.business.slv.service.infrastructure.doris.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawAlarmPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawAlarmDay;
import dst.v2x.business.slv.service.infrastructure.doris.vo.RawAlarmVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author DST
* @description 针对表【slv_raw_alarm_day】的数据库操作Mapper
* @createDate 2025-06-12 17:33:29
* @Entity dst.v2x.business.slv.service.infrastructure.doris.entity.RawAlarmDay
*/
public interface RawAlarmDayMapper extends BaseMapper<RawAlarmDay> {
    /**
     * 批量插入
     * @param list
     */
    void insertBatch(@Param("list") List<RawAlarmDay> list);

    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    IPage<RawAlarmVO> findPage(IPage<RawAlarmDay> page, @Param("query") RawAlarmPageQueryDTO query);
}




