package dst.v2x.business.slv.service.infrastructure.doris.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawDataNav;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
* @author DST
* @description 针对表【slv_raw_data_nav】的数据库操作Mapper
* @createDate 2025-06-10 14:56:12
* @Entity dst.v2x.business.slv.service.infrastructure.doris.entity.RawDataNav
*/
public interface RawDataNavMapper extends BaseMapper<RawDataNav> {
    /**
     * 根据年、月查询该月有哪些天
     */
    List<Integer> queryOfMonth(@Param("vehicleNo") String vehicleNo,
                              @Param("year") LocalDate year,
                              @Param("month") Integer month);

    /**
     * 根据年、月、日查询该日有哪些时段
     */
    List<Integer> queryOfDay(@Param("vehicleNo") String vehicleNo,
                               @Param("year") LocalDate year,
                               @Param("month") Integer month,
                               @Param("day") Integer day);

    /**
     * 批量插入
     * @param list
     */
    void insertBatch(@Param("list") List<RawDataNav> list);
}




