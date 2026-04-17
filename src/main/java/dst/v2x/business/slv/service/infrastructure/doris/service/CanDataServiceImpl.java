package dst.v2x.business.slv.service.infrastructure.doris.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dst.steed.vds.common.domain.response.PageDTO;
import dst.v2x.business.slv.service.common.utils.DstPageUtil;
import dst.v2x.business.slv.service.infrastructure.doris.dto.CanPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.entity.CanData;
import dst.v2x.business.slv.service.infrastructure.doris.mapper.CanDataMapper;
import dst.v2x.business.slv.service.infrastructure.doris.vo.CanDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
* @author DST
* @description 针对表【slv_can_data】的数据库操作Service实现
* @createDate 2025-08-13 16:37:12
*/
@Service
public class CanDataServiceImpl extends ServiceImpl<CanDataMapper, CanData> {
    @Autowired
    private CanDataMapper canDataMapper;

    /**
     * 批量插入
     * @param list
     */
    public void insertBatch(List<CanData> list){
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        canDataMapper.insertBatch(list);
    }

    /**
     * 分页查询
     */
    public PageDTO<CanDataVO> queryPage(CanPageQueryDTO queryDTO) {
        IPage<CanDataVO> page = this.getBaseMapper().findPage(DstPageUtil.toPage(queryDTO), queryDTO);
        return DstPageUtil.toPageDTO(page);
    }
}




