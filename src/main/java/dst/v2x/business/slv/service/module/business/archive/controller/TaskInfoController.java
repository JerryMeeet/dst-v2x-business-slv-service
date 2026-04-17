package dst.v2x.business.slv.service.module.business.archive.controller;

import cn.hutool.core.bean.BeanUtil;
import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.config.excel.CustomExcelExport;
import dst.v2x.business.slv.service.common.domain.acl.excel.ExportResult;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.TaskGoalDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.TaskInfoAddDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.TaskInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.TaskInfoCurrentQueryVO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.TaskInfoExportVO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.TaskInfoPageQueryVO;
import dst.v2x.business.slv.service.module.business.archive.service.TaskInfoBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 档案信息-任务信息
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@RestController
@RequestMapping("/archive/task/info")
public class TaskInfoController {
    @Autowired
    private TaskInfoBusinessService taskInfoBusinessService;

    /**
     * 分页查询任务信息
     *
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/page")
    public Response<PageDTO<TaskInfoPageQueryVO>> queryPage(@RequestBody TaskInfoPageQueryDTO queryDTO) {
        return Response.succeed(taskInfoBusinessService.queryPage(queryDTO));
    }

    /**
     * 查询任务明细列表
     * @param taskId 任务ID
     */
    @GetMapping(value = "/goals")
    public Response<List<TaskGoalDTO>> queryGoals(@RequestParam(value = "taskId") Long taskId) {
        return Response.succeed(taskInfoBusinessService.queryGoals(taskId));
    }

    /**
     * 任务档案导出（前端接口）
     *
     * @param queryDTO
     * @return
     * @throws Exception
     */
    @CustomExcelExport(
            moduleName = "无人车档案信息",
            fileName = "任务档案导出",
            dataUrl = "/slv/archive/task/info/exportExcel/data"
    )
    @PostMapping(value = "/exportExcel")
    public Response<?> exportExcel(@RequestBody TaskInfoPageQueryDTO queryDTO) throws Exception {
        return Response.succeed();
    }

    /**
     * 任务档案导出（后台接口）
     *
     * @param queryDTO
     * @return
     * @ignore
     */
    @PostMapping(value = "/exportExcel/data")
    public Response<ExportResult<TaskInfoExportVO>> exportExcelData(@RequestBody TaskInfoPageQueryDTO queryDTO) {
        PageDTO<TaskInfoPageQueryVO> pageDTO = taskInfoBusinessService.queryPage(queryDTO);
        List<TaskInfoExportVO> exportVOList = pageDTO.getList().stream().map(vo -> BeanUtil.copyProperties(vo, TaskInfoExportVO.class)).toList();
        ExportResult<TaskInfoExportVO> result = ExportResult.wrap(exportVOList, pageDTO.getTotalPage(), TaskInfoExportVO.class);
        return Response.succeed(result);
    }

    /**
     * 新增任务
     */
    @PostMapping(value = "/add")
    public Response<?> add(@Validated @RequestBody TaskInfoAddDTO dto) {
        return taskInfoBusinessService.add(dto);
    }

    /**
     * 查看当前任务
     * @param vehicleNo 车辆编号
     */
    @GetMapping(value = "/current")
    public Response<TaskInfoCurrentQueryVO> queryCurrentTask(@RequestParam(value = "vehicleNo") String vehicleNo) {
        return taskInfoBusinessService.queryCurrentTask(vehicleNo);
    }

    /**
     * 取消任务
     * @param vehicleNo 车辆编号
     */
    @GetMapping(value = "/cancel")
    public Response<?> cancel(@RequestParam(value = "vehicleNo") String vehicleNo) {
        return taskInfoBusinessService.cancel(vehicleNo);
    }
}
