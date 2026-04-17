package dst.v2x.business.slv.service.infrastructure.acl.zelos.api;

import dst.v2x.business.slv.service.infrastructure.acl.zelos.request.ZelosTokenReq;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosResponse;
import dst.v2x.business.slv.service.infrastructure.acl.zelos.response.ZelosTokenRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ZelosAutoCarAuthFeignApi", url = "${zelos.auth.url}", contextId = "ZelosAutoCarAuthFeignApi")
public interface ZelosAutoCarAuthFeignApi {

    /**
     * 获取token
     */
    @PostMapping("/app/accessToken")
    ZelosResponse<ZelosTokenRes> getAccessToken(@RequestBody ZelosTokenReq req);


}
