package dst.v2x.business.slv.service.module.business.alarm.controller;

import com.alibaba.fastjson.JSON;
import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.config.excel.CustomExcelExport;
import dst.v2x.business.slv.service.common.domain.acl.excel.ExportResult;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawAlarmPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.vo.RawAlarmVO;
import dst.v2x.business.slv.service.module.business.alarm.service.AlarmDayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 无人车实时告警数据-页面接口
 *
 * @author 江民来
 * @menu 上报数据
 * @date 2025年06月13日 18:06
 */
@Slf4j
@RestController
@RequestMapping("/alarmDay")
public class AlarmDayController {
    @Autowired
    private AlarmDayService alarmDayService;

    /**
     * 报警实时数据分页查询
     *
     * @author 江民来
     * @date 2025/6/13 18:13
     */
    @PostMapping("/queryPage")
    public Response<PageDTO<RawAlarmVO>> queryPage(@RequestBody @Validated RawAlarmPageQueryDTO dto) {
        log.info(" 告警实时数据分页查询入参->{}", JSON.toJSONString(dto));
        return Response.succeed(alarmDayService.queryPage(dto));
    }

    /**
     * 实时报警信息导出(前端接口)
     *
     * @author 江民来
     * @date 2025/6/17 14:19
     */
    @CustomExcelExport(
            moduleName = "无人车报警信息",
            fileName = "实时报警信息导出",
            dataUrl = "/slv/alarmDay/exportExcelTask"
    )
    @PostMapping("/exportExcel")
    public Response exportExcel(@RequestBody @Validated RawAlarmPageQueryDTO dto) {
        return Response.succeed();
    }

    /**
     * 导出后端接口
     *
     * @param dto
     * @author 江民来
     * @date 2025/6/17 14:49
     */
    @PostMapping("/exportExcelTask")
    public Response<ExportResult<RawAlarmVO>> exportExcelTask(@RequestBody @Validated RawAlarmPageQueryDTO dto) {
        PageDTO<RawAlarmVO> pageDTO = alarmDayService.queryPage(dto);
        ExportResult<RawAlarmVO> result = ExportResult.wrap(pageDTO.getList(), pageDTO.getTotalPage(), RawAlarmVO.class);
        return Response.succeed(result);
    }
}
