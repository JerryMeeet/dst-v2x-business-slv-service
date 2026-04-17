package dst.v2x.business.slv.service.common.feign.configuration;

import dst.v2x.business.slv.service.common.feign.interceptor.NeolixClientRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * 新石器-请求配置
 */
public class NeolixFeignClientConfiguration {
    @Value("${neolix.clientSecret}")
    private String clientSecret;

    @Bean
    public RequestInterceptor NeolixRequestInterceptor(){
        NeolixClientRequestInterceptor neolixClientRequestInterceptor = new NeolixClientRequestInterceptor();
        neolixClientRequestInterceptor.setClientSecret(clientSecret);
        return neolixClientRequestInterceptor;
    }




}
