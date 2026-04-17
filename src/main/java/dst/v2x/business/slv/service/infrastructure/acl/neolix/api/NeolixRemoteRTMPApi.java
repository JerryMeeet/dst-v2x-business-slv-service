package dst.v2x.business.slv.service.infrastructure.acl.neolix.api;

import dst.v2x.business.slv.service.common.feign.configuration.NeolixFeignClientRTMPConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 新石器无人车-RTMP 视频对接 接口请求
 */
@FeignClient(name = "neolixRemoteRTMPApi", url = "${neolix.remote.rtmp.url}", contextId = "NeolixRemoteRTMPApi",
        configuration = NeolixFeignClientRTMPConfiguration.class)
public interface NeolixRemoteRTMPApi {
    /**
     * 获取通道列表
     * @param vid 车架号vin
     * @return
     */
    @GetMapping(value = "/CarNet/getVideoList")
    String getVideoList(@RequestParam(value = "vid") String vid);

    /**
     * 获取rtmp拉流地址
     * @param vid 车架号vin
     * @param videoChannel 通道号
     * @return
     */
    @GetMapping(value = "/CarNet/startVideoPlay")
    String startVideoPlay(@RequestParam(value = "vid") String vid, @RequestParam(value = "videoChannel") Integer videoChannel);

    /**
     * 关闭推流
     * @param vid 车架号vin
     * @param videoChannel 通道号
     * @return
     */
    @GetMapping(value = "/CarNet/stopVideoPlay")
    String stopVideoPlay(@RequestParam(value = "vid") String vid, @RequestParam(value = "videoChannel") Integer videoChannel);
}