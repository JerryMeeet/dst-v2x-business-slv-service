package dst.v2x.business.slv.service.infrastructure.acl.zelos.service;

import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import dst.v2x.business.slv.service.infrastructure.doris.entity.CanData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author pengyl
 * @date 2025/8/8
 * @description
 */
@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
@Slf4j
public class ZelosCanApiServiceTest {
    @Autowired
    private ZelosCanApiService zelosCanApiService;

    /**
     * 根据车辆编号查询Can数据-九识
     */
    @Test
    public void getZelosMapMakeList() {
        List<CanData> list = zelosCanApiService.getZelosCanData(Arrays.asList("ZL05960"));
        log.info("list: {}", DstJsonUtil.toString(list));
    }
}
