package dst.v2x.business.slv.service.infrastructure.biz.rtmp.vo;

import lombok.Data;

import java.util.List;

/**
 * RTMP视频
 */
@Data
public class RtmpVideoVO {
    /**
     * 视频通道
     */
    private String videoChannel;

    /**
     * 视频名称
     */
    private String videoName;

    /**
     * 视频URL
     */
    private List<String> videoUrl;
}