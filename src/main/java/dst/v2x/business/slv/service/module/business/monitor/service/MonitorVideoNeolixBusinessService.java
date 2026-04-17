package dst.v2x.business.slv.service.module.business.monitor.service;

import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixResponse;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.service.NeolixRemoteRTMPApiService;
import dst.v2x.business.slv.service.infrastructure.biz.rtmp.vo.RtmpVideoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 车辆监控-视频-新石器-相关服务
 *
 * @author: pengyunlin
 * @date: 2025/9/10 15:51
 */
@Service
@Slf4j
public class MonitorVideoNeolixBusinessService {
    @Autowired
    private NeolixRemoteRTMPApiService neolixRemoteRTMPApiService;

    /**
     * 查询视频列表
     */
    public Response<List<RtmpVideoVO>> getVideoList(String vin){
        if (StringUtils.isEmpty(vin)) {
            return Response.error("VIN不能为空");
        }
        //调用三方接口获取通道列表
        NeolixResponse<List<RtmpVideoVO>> apiResponse = neolixRemoteRTMPApiService.getVideoList(vin);
        if (!apiResponse.getSuccess()) {
            return Response.error(apiResponse.getMsg());
        }
        List<RtmpVideoVO> voList = apiResponse.getData();
        for (RtmpVideoVO vo : voList) {
            //调用三方接口获取视频地址
            NeolixResponse<List<String>> videoUrlResponse = neolixRemoteRTMPApiService.getVideoUrl(vin, Integer.valueOf(vo.getVideoChannel()));
            if (!videoUrlResponse.getSuccess()) {
                return Response.error(videoUrlResponse.getMsg());
            }
            vo.setVideoUrl(videoUrlResponse.getData());
        }
        return Response.succeed(voList);
    }

    /**
     * 关闭视频
     * @param vin 车辆VIN
     * @param videoChannelList 视频通道列表
     */
    public Response<?> stopVideo(String vin, List<String> videoChannelList) {
        if (StringUtils.isEmpty(vin)) {
            return Response.error("VIN不能为空");
        }
        if(CollectionUtils.isEmpty(videoChannelList)){
            return Response.error("视频通道列表不能为空");
        }
        for (String channel : videoChannelList) {
            NeolixResponse<Void> response = neolixRemoteRTMPApiService.stopVideoPlay(vin, Integer.valueOf(channel));
            if (!response.getSuccess()) {
                return Response.error(response.getMsg());
            }
        }
        return Response.succeed();
    }
}
