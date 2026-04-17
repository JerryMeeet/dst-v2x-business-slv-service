package dst.v2x.business.slv.service.infrastructure.biz.archive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.StationInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.StationInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.StationInfoPageQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author DST
* @description 针对表【slv_station_info(站点信息表)】的数据库操作Mapper
* @createDate 2025-07-03 11:13:50
* @Entity dst.v2x.business.slv.service.infrastructure.biz.archive.entity.StationInfo
*/
public interface StationInfoMapper extends BaseMapper<StationInfo> {
    /**
     * 批量新增
     */
    int batchAdd(@Param("list") List<StationInfo> list);

    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    IPage<StationInfoPageQueryVO> findPage(IPage<StationInfo> page, @Param("query") StationInfoPageQueryDTO query);
}




