package dst.v2x.business.slv.service.common.feign.configuration;

import dst.v2x.business.slv.service.common.feign.interceptor.NeolixClientRTMPRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * 新石器-请求配置-RTMP 视频对接
 */
public class NeolixFeignClientRTMPConfiguration {
    @Value("${neolix.rtmp.token}")
    private String token;

    @Bean
    public RequestInterceptor NeolixRequestInterceptor(){
        NeolixClientRTMPRequestInterceptor rtmpRequestInterceptor = new NeolixClientRTMPRequestInterceptor();
        rtmpRequestInterceptor.setToken(token);
        return rtmpRequestInterceptor;
    }
}
