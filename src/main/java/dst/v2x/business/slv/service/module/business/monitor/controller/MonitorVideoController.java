package dst.v2x.business.slv.service.module.business.monitor.controller;

import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.infrastructure.biz.rtmp.vo.RtmpVideoVO;
import dst.v2x.business.slv.service.module.business.monitor.service.MonitorVideoBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 车辆监控-视频
 *
 * @author: pengyunlin
 * @date: 2025/9/10 15:51
 */
@RestController
@RequestMapping("/monitor/video")
public class MonitorVideoController {
    @Autowired
    private MonitorVideoBusinessService monitorVideoBusinessService;

    /**
     * 查询列表
     * @param vehicleNo 车辆编码
     * @return
     */
    @GetMapping(value = "/getVideoList")
    public Response<List<RtmpVideoVO>> getVideoList(String vehicleNo) {
        return monitorVideoBusinessService.getVideoList(vehicleNo);
    }

    /**
     * 关闭
     * @param vehicleNo 车辆编码
     * @param videoChannelList 视频通道编码集合
     */
    @GetMapping(value = "/stopVideo")
    public Response<?> stopVideo(@RequestParam(value = "vehicleNo") String vehicleNo,
                                 @RequestParam(value = "videoChannelList") List<String> videoChannelList) {
        return monitorVideoBusinessService.stopVideo(vehicleNo, videoChannelList);
    }

}
