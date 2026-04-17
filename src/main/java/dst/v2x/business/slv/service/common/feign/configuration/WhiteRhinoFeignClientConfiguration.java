package dst.v2x.business.slv.service.common.feign.configuration;

import dst.v2x.business.slv.service.common.feign.interceptor.WhiteRhinoClientRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * 白犀牛-请求配置
 */
public class WhiteRhinoFeignClientConfiguration {

    @Value("${baixiniu.remote.appKey}")
    private String appKey;

    @Value("${baixiniu.remote.appSecret}")
    private String appSecret;

    @Bean
    public RequestInterceptor whiteRhinoClientRequestInterceptor(){
        WhiteRhinoClientRequestInterceptor whiteRhinoClientRequestInterceptor = new WhiteRhinoClientRequestInterceptor();
        whiteRhinoClientRequestInterceptor.setAppKey(appKey);
        whiteRhinoClientRequestInterceptor.setAppSecret(appSecret);
        return whiteRhinoClientRequestInterceptor;
    }


}
