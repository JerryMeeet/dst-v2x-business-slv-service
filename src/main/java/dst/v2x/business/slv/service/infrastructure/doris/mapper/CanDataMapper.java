package dst.v2x.business.slv.service.infrastructure.doris.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.VehicleInfo;
import dst.v2x.business.slv.service.infrastructure.doris.dto.CanPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.entity.CanData;
import dst.v2x.business.slv.service.infrastructure.doris.vo.CanDataVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author pengyl
* @description 针对表【slv_can_data】的数据库操作Mapper
* @createDate 2025-06-09 16:37:12
* @Entity dst.v2x.business.slv.service.infrastructure.doris.entity.CanData
*/
public interface CanDataMapper extends BaseMapper<CanData> {
    /**
     * 批量插入
     * @param list
     */
    void insertBatch(@Param("list") List<CanData> list);

    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    IPage<CanDataVO> findPage(IPage<VehicleInfo> page, @Param("query") CanPageQueryDTO query);
}




