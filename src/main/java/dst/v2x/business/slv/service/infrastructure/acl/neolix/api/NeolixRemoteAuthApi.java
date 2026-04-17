package dst.v2x.business.slv.service.infrastructure.acl.neolix.api;

import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixTokenRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 新石器无人车-鉴权 接口请求
 */
@FeignClient(name ="NeolixRemoteAuthApi", url = "${neolix.remote.tokenUrl}", contextId = "NeolixRemoteAuthApi")
public interface NeolixRemoteAuthApi {
    /**
     * 获取车辆列表
     **/
    @GetMapping(value = "/auth/oauth/token")
    NeolixTokenRes getToken(
            @RequestParam(value = "grant_type") String grantType,
            @RequestParam(value = "client_id") String clientId,
            @RequestParam(value = "client_secret") String clientSecret);


}
