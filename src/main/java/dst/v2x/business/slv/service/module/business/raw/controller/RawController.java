package dst.v2x.business.slv.service.module.business.raw.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.config.excel.CustomExcelExport;
import dst.v2x.business.slv.service.common.domain.acl.excel.ExportResult;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawListQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.vo.RawDataExportVO;
import dst.v2x.business.slv.service.infrastructure.doris.vo.RawDataVO;
import dst.v2x.business.slv.service.module.business.raw.service.RawDataBusinessService;
import dst.v2x.business.slv.service.module.business.raw.service.RawDataNeolixBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

/**
 * 上报数据
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@RestController
@RequestMapping("/raw")
@Slf4j
public class RawController {
    @Autowired
    private RawDataBusinessService rawDataBusinessService;
    @Autowired
    private RawDataNeolixBusinessService rawDataNeolixBusinessService;

    /**
     * 查询最后上报数据列表
     */
    @GetMapping(value = "/queryLatestList")
    public Response<List<RawDataVO>> queryLatestList() {
        return Response.succeed(rawDataBusinessService.queryLatestList());
    }

    /**
     * 查询指定车辆最后上报数据
     */
    @GetMapping(value = "/queryLatest")
    public Response<RawDataVO> queryLatest(@RequestParam String vehicleNo) {
        RawDataVO rawDataVO = rawDataBusinessService.queryLatest(vehicleNo);
        if (Objects.isNull(rawDataVO)) {
            return Response.error("车辆上报数据不存在");
        }
        return Response.succeed(rawDataVO);
    }

    /**
     * 查询指定车辆实时数据
     */
    @GetMapping(value = "/queryLatestRealTime")
    public Response<RawDataVO> queryLatestRealTime(@RequestParam String vehicleNo) {
        RawDataVO rawDataVO = rawDataNeolixBusinessService.querLatestRealTime(vehicleNo);
        if (Objects.isNull(rawDataVO)) {
            return Response.error("车辆上报数据不存在");
        }
        return Response.succeed(rawDataVO);
    }

    /**
     * 查询无人车历史数据(不分页)
     *
     * @param queryDTO
     * @return Response
     * @author 江民来
     * @date 2024/11/16 9:50
     */
    @PostMapping("/queryList")
    public Response<List<RawDataVO>> queryList(@Validated @RequestBody RawListQueryDTO queryDTO) {
        log.info("打印查询无人车历史数据(不分页)入参->{}", JSON.toJSONString(queryDTO));
        // 校验日期是不是一天之内
        Duration duration = Duration.between(queryDTO.getStartDataTime(), queryDTO.getEndDataTime());
        if (duration.toDays() >= 1) {
            return Response.error("查询日期不能超过一天");
        }
        return Response.succeed(rawDataBusinessService.queryList(queryDTO));
    }

    /**
     * 查询无人车历史数据(分页)
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/queryPage")
    public Response<PageDTO<RawDataVO>> queryPage(@Validated @RequestBody RawPageQueryDTO queryDTO) {
        return Response.succeed(rawDataBusinessService.queryPage(queryDTO));
    }

    /**
     * 历史数据导出（前端接口）
     * @param queryDTO
     * @return
     * @throws Exception
     */
    @CustomExcelExport(
            moduleName = "无人车上报数据",
            fileName = "历史状态数据导出",
            dataUrl = "/slv/raw/exportExcel/data"
    )
    @PostMapping(value = "/exportExcel")
    public Response<?> exportExcel(@RequestBody RawPageQueryDTO queryDTO) throws Exception {
        return Response.succeed();
    }

    /**
     * 历史数据导出（后台接口）
     * @ignore
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/exportExcel/data")
    public Response<ExportResult<RawDataExportVO>> exportExcelData(@RequestBody RawPageQueryDTO queryDTO) {
        PageDTO<RawDataVO> pageDTO = rawDataBusinessService.queryPage(queryDTO);
        List<RawDataExportVO> exportVOList = pageDTO.getList().stream().map(vo -> BeanUtil.copyProperties(vo, RawDataExportVO.class)).toList();
        ExportResult<RawDataExportVO> result = ExportResult.wrap(exportVOList, pageDTO.getTotalPage(), RawDataExportVO.class);
        return Response.succeed(result);
    }
}
