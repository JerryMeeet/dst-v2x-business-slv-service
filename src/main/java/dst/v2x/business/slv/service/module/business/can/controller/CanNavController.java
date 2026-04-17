package dst.v2x.business.slv.service.module.business.can.controller;

import com.alibaba.fastjson.JSON;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.infrastructure.doris.dto.CanNavListQueryDTO;
import dst.v2x.business.slv.service.module.business.can.service.CanDataNavBusinessService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CAN数据-导航
 * @author: pengyunlin
 * @date: 2025/8/14 15:51
 */
@RestController
@RequestMapping("/can/nav")
@Slf4j
public class CanNavController {
    @Autowired
    private CanDataNavBusinessService canDataNavBusinessService;

    /**
     * 根据年、月查询该月有哪些天
     */
    @PostMapping("/queryOfMonth")
    public Response<List<String>> queryOfMonth(@Valid @RequestBody CanNavListQueryDTO queryDTO) {
        log.info("打印无人车CAN历史数据根据年、月查询天-入参->{}", JSON.toJSONString(queryDTO));
        return Response.succeed(canDataNavBusinessService.queryOfMonth(queryDTO));
    }

    /**
     * 根据天查询小时
     */
    @PostMapping("/queryOfDay")
    public Response<List<String>> queryOfDay(@Valid @RequestBody CanNavListQueryDTO queryDTO) {
        log.info("打印无人车CAN历史数据根据年、月、日查询小时-入参->{}", JSON.toJSONString(queryDTO));
        return Response.succeed(canDataNavBusinessService.queryOfDay(queryDTO));
    }
}
