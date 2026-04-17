package dst.v2x.business.slv.service.infrastructure.biz.archive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.MapMakePageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.MapMake;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.MapMakePageQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author DST
* @description 针对表【slv_map_make(站点信息表)】的数据库操作Mapper
* @createDate 2025-07-03 11:13:50
* @Entity dst.v2x.business.slv.service.infrastructure.biz.archive.entity.MapMake
*/
public interface MapMakeMapper extends BaseMapper<MapMake> {
    /**
     * 批量新增
     */
    int batchAdd(@Param("list") List<MapMake> list);

    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    IPage<MapMakePageQueryVO> findPage(IPage<MapMake> page, @Param("query") MapMakePageQueryDTO query);
}




