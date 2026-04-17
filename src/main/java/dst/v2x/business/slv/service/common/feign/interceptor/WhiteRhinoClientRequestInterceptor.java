package dst.v2x.business.slv.service.common.feign.interceptor;

import cn.hutool.crypto.digest.DigestUtil;
import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.infrastructure.acl.whiterhino.request.WhiteRhinoRequest;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;


/**
 * 白犀牛-请求配置拦截器
 */
@Slf4j
public class WhiteRhinoClientRequestInterceptor implements RequestInterceptor {

    private String appKey;

    private String appSecret;

    @Override
    public void apply(RequestTemplate requestTemplate) {

        Request request = requestTemplate.request();
        Long timestamp = System.currentTimeMillis();

        // 获取RequestTemplate请求体
        String body = new String(request.body(), StandardCharsets.UTF_8);

        // 计算sign
        String signString = String.format("appKey%sdata%stimestamp%sappSecret%s", appKey, body, timestamp, appSecret);
        String sign = DigestUtil.md5Hex(signString);

        // 构造请求参数
        WhiteRhinoRequest whiteRhinoRequest = new WhiteRhinoRequest();
        whiteRhinoRequest.setAppKey(appKey);
        whiteRhinoRequest.setData(body);
        whiteRhinoRequest.setSign(sign);
        whiteRhinoRequest.setTimeStamp(timestamp);

        // 执行请求
        log.debug("请求地址url：{}，请求参数：{}", requestTemplate.feignTarget().url() + requestTemplate.url(), DstJsonUtil.toString(whiteRhinoRequest));
        if (request.httpMethod().equals(Request.HttpMethod.POST)) {
            requestTemplate.body(Request.Body.encoded(DstJsonUtil.toString(whiteRhinoRequest).getBytes(StandardCharsets.UTF_8), requestTemplate.requestCharset()));
        }
    }

    /**
     * 设置query参数
     *
     * @param requestTemplate
     * @return
     */
    private String setSign(RequestTemplate requestTemplate) {

        Request request = requestTemplate.request();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String signString = String.format("appKey%sdata%stimestamp%sappSecret%s", appKey, DstJsonUtil.toString(request.body()), timestamp, appSecret);
        String sign = Arrays.toString(DigestUtil.md5(signString));

        return sign;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

/*    public static void main(String[] args) {
        String timestamp = String.valueOf(System.currentTimeMillis());
    }*/
}
