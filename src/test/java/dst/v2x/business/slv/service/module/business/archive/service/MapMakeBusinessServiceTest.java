// AiAutoGenerateStart_yT9Q3pL7mNvH2sR8wE4xZ6cK1oA0uJ5d
package dst.v2x.business.slv.service.module.business.archive.service;

import com.dst.steed.vds.common.domain.response.PageDTO;
import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.MapMakePageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.MapMakePageQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 档案信息-路线相关服务测试类
 *
 * @author: lingma
 * @date: 2025/8/22 15:51
 */
@Slf4j
@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
class MapMakeBusinessServiceTest {

    @Autowired
    private MapMakeBusinessService mapMakeBusinessService;

    /**
     * 拉取九识路线
     */
    @Test
    void updateZelosStationInfo() {
        PageDTO<MapMakePageQueryVO> page = mapMakeBusinessService.queryPage(new MapMakePageQueryDTO());
        log.info("{}", page);
    }
}
// AiAutoGenerateEnd_yT9Q3pL7mNvH2sR8wE4xZ6cK1oA0uJ5d