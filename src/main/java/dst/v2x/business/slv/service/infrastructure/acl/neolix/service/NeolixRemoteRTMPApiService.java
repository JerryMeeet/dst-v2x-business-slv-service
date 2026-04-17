package dst.v2x.business.slv.service.infrastructure.acl.neolix.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.dst.steed.vds.common.util.DstJsonUtil;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.api.NeolixRemoteRTMPApi;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.response.NeolixResponse;
import dst.v2x.business.slv.service.infrastructure.biz.rtmp.vo.RtmpVideoVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 新石器无人车远程-RTMP 视频对接 接口服务
 */
@Slf4j
@Service
public class NeolixRemoteRTMPApiService {
    @Resource
    private NeolixRemoteRTMPApi neolixRemoteRTMPApi;

    /**
     * 关闭推流
     */
    public NeolixResponse<Void> stopVideoPlay(String vin, Integer videoChannel) {
        String jsonStr = null;
        try {
            jsonStr = neolixRemoteRTMPApi.stopVideoPlay(vin, videoChannel);
        } catch (Exception e) {
            log.warn("关闭推流请求失败",e);
            // 构建错误响应 是为了远程调用失败时不弹出token敏感信息
            NeolixResponse<Void> errorResponse = new NeolixResponse<>();
            /*errorResponse.setCode("500");*/
            errorResponse.setMsg("关闭推流失败");
            errorResponse.setSuccess(false);
            return errorResponse;
        }
        log.debug("关闭推流: {}", jsonStr);

        //json转对象
        NeolixResponse<Object> apiResponse = jsonToResponse(jsonStr);
        NeolixResponse<Void> response = new NeolixResponse<>();
        BeanUtil.copyProperties(apiResponse, response);
        return response;
    }

    /**
     * 获取rtmp拉流地址
     */
    public NeolixResponse<List<String>> getVideoUrl(String vin, Integer videoChannel) {
        String jsonStr = neolixRemoteRTMPApi.startVideoPlay(vin, videoChannel);
        log.debug("获取rtmp拉流地址jsonStr: {}", jsonStr);

        //json转对象
        NeolixResponse<Object> apiResponse = jsonToResponse(jsonStr);
        NeolixResponse<List<String>> response = new NeolixResponse<>();
        BeanUtil.copyProperties(apiResponse, response);

        if (!apiResponse.getSuccess()) {
            return response;
        }

        // 安全地处理数据转换
        if (apiResponse.getData() == null) {
            return response;
        }
        List<String> urlList = new ArrayList<>();
        try {
            Map<String, Object> map = (Map<String, Object>) apiResponse.getData();
            map.forEach((k, v)->urlList.add(Objects.toString(v)));
        } catch (ClassCastException e) {
            log.warn("从响应中提取rtmp拉流地址失败", e);
        }
        response.setData(urlList);
        return response;
    }

    /**
     * 获取通道列表
     * @param vin
     * @return
     */
    public NeolixResponse<List<RtmpVideoVO>> getVideoList(String vin) {
        String jsonStr = neolixRemoteRTMPApi.getVideoList(vin);
        log.debug("获取通道列表jsonStr: {}", jsonStr);

        //json转对象
        NeolixResponse<Object> apiResponse = jsonToResponse(jsonStr);
        NeolixResponse<List<RtmpVideoVO>> response = new NeolixResponse<>();
        BeanUtil.copyProperties(apiResponse, response);

        if (!apiResponse.getSuccess()) {
            return response;
        }

        // 安全地处理数据转换
        List<RtmpVideoVO> videoList = null;
        if (apiResponse.getData() == null) {
            return response;
        }
        try {
            /*Map<String, Object> map = (Map<String, Object>) apiResponse.getData();*/
            Map<String, Object> map =  JSONObject.parseObject(jsonStr);
            Object videoListObj = map.get("video_list");
            if (videoListObj instanceof List) {
                List<Map<String, Object>> videoListData = (List<Map<String, Object>>) videoListObj;
                videoList = videoListData.stream()
                        .map(d->{
                            RtmpVideoVO vo = new RtmpVideoVO();
                            vo.setVideoChannel(Objects.toString(d.get("videoChannel"), ""));
                            vo.setVideoName(Objects.toString(d.get("videoName"), ""));
                            return vo;
                        })
                        .collect(Collectors.toList());
            }
        } catch (ClassCastException e) {
            log.warn("从响应中提取视频列表失败", e);
        }
        response.setData(videoList);
        return response;
    }

    /**
     * json转对象
     */
    private NeolixResponse<Object> jsonToResponse(String jsonStr) {
        Map<String, Object> map = DstJsonUtil.toMap(jsonStr, String.class, Object.class);
        Object code = map.get("code");

        NeolixResponse<Object> response = new NeolixResponse<>();

        if (code != null) {
            boolean isSuccess = "200".equals(code.toString());
            response.setSuccess(isSuccess);
            response.setCode(code.toString());
            response.setMsg(ObjectUtil.toString(map.get("msg")));
            if (map.containsKey("data")) {
                response.setData(map.get("data"));
            } else if (map.containsKey("video_list")) {
                response.setData(map.get("video_list"));
            }
        } else {
            response.setSuccess(true);
            response.setMsg(ObjectUtil.toString(map.get("msg")));
            response.setData(map);
        }
        return response;
    }
}