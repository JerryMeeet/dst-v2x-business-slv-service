package dst.v2x.business.slv.service.common.feign.interceptor;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Objects;


/**
 * 新石器-RTMP 视频对接-请求配置拦截器
 */
@Slf4j
public class NeolixClientRTMPRequestInterceptor implements RequestInterceptor {

    private String token;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Request request = requestTemplate.request();
        // 构造请求参数
        String param;
        if(requestTemplate.url().contains("?")){
            param = "&";
        }else {
            param = "?";
        }
        param += "token=" + token;
        String requestBodyStr;
        if (Objects.isNull(request.body())) {
            requestBodyStr = "";
        }else {
            requestBodyStr = new String(request.body(), StandardCharsets.UTF_8);
        }

        // 执行请求
        log.debug("新石器请求地址url：{}，请求参数：{}", requestTemplate.feignTarget().url() + requestTemplate.url() + param, requestBodyStr);
        String requestUrl = requestTemplate.url() + param;
        requestTemplate.uri(requestUrl);

    }

    public void setToken(String token) {
        this.token = token;
    }
}
