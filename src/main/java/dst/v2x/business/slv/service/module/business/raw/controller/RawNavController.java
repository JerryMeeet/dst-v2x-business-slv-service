package dst.v2x.business.slv.service.module.business.raw.controller;

import com.alibaba.fastjson.JSON;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawNavListQueryDTO;
import dst.v2x.business.slv.service.module.business.raw.service.RawDataNavBusinessService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 上报数据-导航
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@RestController
@RequestMapping("/raw/nav")
@Slf4j
public class RawNavController {
    @Autowired
    private RawDataNavBusinessService rawDataNavBusinessService;

    /**
     * 根据年、月查询该月有哪些天
     *
     * @return Response
     * @author 江民来
     * @date 2024/11/8 16:54
     */
    @PostMapping("/queryOfMonth")
    public Response<List<String>> queryOfMonth(@Valid @RequestBody RawNavListQueryDTO queryDTO) {
        log.info("打印无人车历史数据根据年、月查询天-入参->{}", JSON.toJSONString(queryDTO));
        return Response.succeed(rawDataNavBusinessService.queryOfMonth(queryDTO));
    }

    /**
     * 根据天查询小时
     *
     * @return Response
     * @author 江民来
     * @date 2024/11/8 16:54
     */
    @PostMapping("/queryOfDay")
    public Response<List<String>> queryOfDay(@Valid @RequestBody RawNavListQueryDTO queryDTO) {
        log.info("打印无人车历史数据根据年、月、日查询小时-入参->{}", JSON.toJSONString(queryDTO));
        return Response.succeed(rawDataNavBusinessService.queryOfDay(queryDTO));
    }
}
