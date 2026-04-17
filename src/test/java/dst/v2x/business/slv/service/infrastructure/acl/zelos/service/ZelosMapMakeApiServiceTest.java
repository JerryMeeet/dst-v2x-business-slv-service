package dst.v2x.business.slv.service.infrastructure.acl.zelos.service;

import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.MapMake;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author pengyl
 * @date 2025/8/8
 * @description
 */
@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
@Slf4j
public class ZelosMapMakeApiServiceTest {
    @Autowired
    private ZelosMapMakeApiService zelosMapMakeApiService;

    /**
     * 获取路线列表-九识
     */
    @Test
    public void getZelosMapMakeList() {
        List<MapMake> list = zelosMapMakeApiService.getZelosMapMakeList();
        log.info("list: {}", DstJsonUtil.toString(list));
    }
}
