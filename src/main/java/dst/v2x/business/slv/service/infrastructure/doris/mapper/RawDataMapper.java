package dst.v2x.business.slv.service.infrastructure.doris.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.VehicleInfo;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawData;
import dst.v2x.business.slv.service.infrastructure.doris.vo.RawDataVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author pengyl
* @description 针对表【slv_raw_data】的数据库操作Mapper
* @createDate 2025-06-09 16:37:12
* @Entity dst.v2x.business.slv.service.infrastructure.doris.entity.RawData
*/
public interface RawDataMapper extends BaseMapper<RawData> {
    /**
     * 批量插入
     * @param list
     */
    void insertBatch(@Param("list") List<RawData> list);

    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    IPage<RawDataVO> findPage(IPage<VehicleInfo> page, @Param("query") RawPageQueryDTO query);
}




