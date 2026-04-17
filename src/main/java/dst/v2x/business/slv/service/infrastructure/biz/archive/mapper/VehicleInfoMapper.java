package dst.v2x.business.slv.service.infrastructure.biz.archive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.VehicleInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.VehicleInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.VehicleInfoPageQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author pengyunlin
* @description 针对表【slv_vehicle_info(车辆信息表)】的数据库操作Mapper
* @createDate 2025-06-06 10:40:40
* @Entity dst.v2x.business.slv.service.infrastructure.biz.archive.entity.VehicleInfo
*/
public interface VehicleInfoMapper extends BaseMapper<VehicleInfo> {
    /**
     * 批量新增
     */
    int batchAdd(@Param("list") List<VehicleInfo> list);

    /**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     */
    IPage<VehicleInfoPageQueryVO> findPage(IPage<VehicleInfo> page, @Param("query") VehicleInfoPageQueryDTO query);

    /**
     * 分页查询-编码
     *
     * @param page
     * @param query
     * @return
     */
    IPage<String> findNoPage(IPage<String> page, @Param("query") VehicleInfoPageQueryDTO query);

    /**
     * 分页查询-车架号
     *
     * @param page
     * @param query
     * @return
     */
    IPage<String> findVinPage(IPage<String> page, @Param("query") VehicleInfoPageQueryDTO query);
}




