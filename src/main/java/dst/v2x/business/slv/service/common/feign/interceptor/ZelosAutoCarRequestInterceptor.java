package dst.v2x.business.slv.service.common.feign.interceptor;

import com.dst.steed.vds.common.util.DstSpringUtil;
import dst.v2x.business.slv.service.module.business.archive.service.VehicleInfoZelosBusinessService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author 江民来
 * @date 2025年02月10日 15:21
 */
@Slf4j
public class ZelosAutoCarRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate request) {

        VehicleInfoZelosBusinessService zelosBusinessService = DstSpringUtil.getBean(VehicleInfoZelosBusinessService.class);

        // 获取到九识无人车的接口Token
        String zelosToken = zelosBusinessService.getZelosToken();

        // 放入请求头
        request.header("token", zelosToken);

        String requestBodyStr;
        if (Objects.isNull(request.body())) {
            requestBodyStr = "";
        }else {
            requestBodyStr = new String(request.body(), StandardCharsets.UTF_8);
        }
        log.debug("九识请求地址url：{}，请求参数：{}，token：{}", request.feignTarget().url() + request.url(), requestBodyStr, zelosToken);
    }
}
