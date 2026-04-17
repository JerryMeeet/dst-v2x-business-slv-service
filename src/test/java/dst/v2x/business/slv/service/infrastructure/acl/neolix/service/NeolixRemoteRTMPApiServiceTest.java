package dst.v2x.business.slv.service.infrastructure.acl.neolix.service;

import dst.v2x.business.slv.service.DstV2xBusinessSlvApplication;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixResponse;
import dst.v2x.business.slv.service.infrastructure.biz.rtmp.vo.RtmpVideoVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author pengyl
 * @date 2025/9/10
 * @description 新石器无人车远程-RTMP 视频对接 接口服务测试类
 */
@SpringBootTest(classes = DstV2xBusinessSlvApplication.class)
@Slf4j
public class NeolixRemoteRTMPApiServiceTest {
    @Autowired
    private NeolixRemoteRTMPApiService neolixRemoteRTMPApiService;

    /**
     * 获取通道列表
     */
    @Test
    public void getVideoList() {
        NeolixResponse<List<RtmpVideoVO>> response = neolixRemoteRTMPApiService.getVideoList("LHTBY2B28PY8EA008");
        log.info("response: {}", response);
    }

    /**
     * 获取rtmp拉流地址
     */
    @Test
    public void getVideoUrl() {
        NeolixResponse<List<String>> response = neolixRemoteRTMPApiService.getVideoUrl("LHTBY2B28PY8EA008", 1);
        log.info("response: {}", response);
    }

    /**
     * 关闭推流
     */
    @Test
    public void stopVideoPlay() {
        NeolixResponse<Void> response = neolixRemoteRTMPApiService.stopVideoPlay("LHTBY2B28PY8EA008", 1);
        log.info("response: {}", response);
    }
}
