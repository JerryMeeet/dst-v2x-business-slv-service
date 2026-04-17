package dst.v2x.business.slv.service.infrastructure.acl.neolix.api;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author pengyl
 * @date 2025/9/9
 * @description 新石器无人车-RTMP 视频对接 接口请求
 */
@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
@Slf4j
public class NeolixRemoteRTMPApiTest {
    @Autowired
    private NeolixRemoteRTMPApi neolixRemoteRTMPApi;

    /**
     * 获取站点信息
     */
    @Test
    public void getVideoList(){
        String response = neolixRemoteRTMPApi.getVideoList("LHTBY2B28PY8EA008");
        log.info("response: {}", response);
    }
}
