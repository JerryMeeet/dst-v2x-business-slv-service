package dst.v2x.business.slv.service.common.config.interceptors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebFilterConfig implements WebMvcConfigurer {
    @Bean
    protected DataRepairIntercept dataRepairIntercept() {
        return new DataRepairIntercept();
    }

    // 多个拦截器组成一个拦截器链
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // temp接口认证拦截
        registry.addInterceptor(dataRepairIntercept()).addPathPatterns("/dataRepair/**");
    }
}
