package dst.v2x.business.slv.service.infrastructure.doris.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dst.steed.vds.common.domain.response.PageDTO;
import dst.v2x.business.slv.service.common.utils.DstPageUtil;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawListQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.entity.RawData;
import dst.v2x.business.slv.service.infrastructure.doris.mapper.RawDataMapper;
import dst.v2x.business.slv.service.infrastructure.doris.vo.RawDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
* @author DST
* @description 针对表【slv_raw_data】的数据库操作Service实现
* @createDate 2025-06-09 16:37:12
*/
@Service
public class RawDataServiceImpl extends ServiceImpl<RawDataMapper, RawData> {
    @Autowired
    private RawDataMapper rawDataMapper;

    /**
     * 查询无人车历史数据(不分页)
     */
    public List<RawDataVO> queryList(RawListQueryDTO queryDTO){
        LambdaQueryWrapper<RawData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(RawData::getDataTime, queryDTO.getStartDataTime())
                .le(RawData::getDataTime, queryDTO.getEndDataTime())
                .eq(RawData::getVehicleNo, queryDTO.getVehicleNo());
        return BeanUtil.copyToList(list(queryWrapper), RawDataVO.class);
    }

    /**
     * 分页查询
     *
     * @author: pengyunlin
     * @date: 2025/6/6 15:51
     */
    public PageDTO<RawDataVO> queryPage(RawPageQueryDTO queryDTO) {
        IPage<RawDataVO> page = this.getBaseMapper().findPage(DstPageUtil.toPage(queryDTO), queryDTO);
        return DstPageUtil.toPageDTO(page);
    }

    /**
     * 批量插入
     * @param list
     */
    public void insertBatch(List<RawData> list){
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        rawDataMapper.insertBatch(list);
    }
}




