package dst.v2x.business.slv.service.module.business.alarm.controller;

import com.alibaba.fastjson.JSON;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.config.excel.CustomExcelExport;
import dst.v2x.business.slv.service.common.domain.acl.excel.ExportResult;
import dst.v2x.business.slv.service.common.enums.raw.RawAlarmTypeEnum;
import dst.v2x.business.slv.service.infrastructure.biz.alarm.vo.AlarmEnumVo;
import dst.v2x.business.slv.service.infrastructure.doris.dto.RawAlarmListQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.vo.RawAlarmVO;
import dst.v2x.business.slv.service.module.business.alarm.service.AlarmHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 无人车历史告警数据-页面接口
 *
 * @author 江民来
 * @menu 上报数据
 * @date 2025年06月13日 18:06
 */
@Slf4j
@RestController
@RequestMapping("/alarmHistory")
public class AlarmHistoryController {

    @Autowired
    private AlarmHistoryService alarmHistoryService;

    /**
     * 报警历史数据列表查询
     *
     * @author 江民来
     * @date 2025/6/13 18:13
     */
    @PostMapping("/queryList")
    public Response<List<RawAlarmVO>> queryList(@RequestBody @Validated RawAlarmListQueryDTO dto) {
        log.info("无人车历史告警数据查询入参->{}", JSON.toJSONString(dto));
        return Response.succeed(alarmHistoryService.queryList(dto));
    }

    /**
     * 获取页面报警类型下拉框(通用历史报警和实时报警下拉框)
     *
     * @author 江民来
     * @date 2025/6/17 13:53
     */
    @GetMapping("/getAlarmTypeList")
    public Response<List<AlarmEnumVo>> queryAlarmTypeList() {
        return Response.succeed(RawAlarmTypeEnum.toEnumVOList());
    }

    /**
     * 历史报警导出(前端接口)
     *
     * @author 江民来
     * @date 2025/6/17 14:19
     */
    @CustomExcelExport(
            moduleName = "无人车报警信息",
            fileName = "历史报警信息导出",
            dataUrl = "/slv/alarmHistory/exportExcelTask"
    )
    @PostMapping("/exportExcel")
    public Response exportExcel(@RequestBody @Validated RawAlarmListQueryDTO dto) {
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
    public Response<ExportResult<RawAlarmVO>> exportExcelTask(@RequestBody @Validated RawAlarmListQueryDTO dto) {
        List<RawAlarmVO> exportVOList = alarmHistoryService.queryList(dto);
        ExportResult<RawAlarmVO> result = ExportResult.wrap(exportVOList, 1L, RawAlarmVO.class);
        return Response.succeed(result);
    }
}
