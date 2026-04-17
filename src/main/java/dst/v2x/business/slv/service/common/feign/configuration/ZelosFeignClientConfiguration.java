package dst.v2x.business.slv.service.common.feign.configuration;

import dst.v2x.business.slv.service.common.feign.interceptor.ZelosAutoCarRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @author 江民来
 * @date 2025年02月10日 16:55
 */
public class ZelosFeignClientConfiguration {

    @Bean
    public RequestInterceptor ZelosRequestInterceptor() {
        return new ZelosAutoCarRequestInterceptor();
    }
}
