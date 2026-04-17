package dst.v2x.business.slv.service.module.business.archive.service;

import com.dst.steed.vds.common.domain.response.PageDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.MapMakePageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.service.MapMakeServiceImpl;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.MapMakePageQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 档案信息-路线相关服务
 *
 * @author: pengyunlin
 * @date: 2025/8/22 15:51
 */
@Service
@Slf4j
public class MapMakeBusinessService {
    @Autowired
    private MapMakeServiceImpl mapMakeService;

    /**
     * 分页查询路线
     *
     * @param queryDTO
     * @return
     */
    public PageDTO<MapMakePageQueryVO> queryPage(MapMakePageQueryDTO queryDTO) {
        return mapMakeService.queryPage(queryDTO);
    }
}
