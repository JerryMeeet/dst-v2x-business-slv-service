package dst.v2x.business.slv.service.module.business.archive.controller;

import cn.hutool.core.bean.BeanUtil;
import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.config.excel.CustomExcelExport;
import dst.v2x.business.slv.service.common.domain.acl.excel.ExportResult;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.VehicleInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.VehicleInfoExportVO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.VehicleInfoPageQueryVO;
import dst.v2x.business.slv.service.module.business.archive.service.VehicleInfoBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 档案信息-车辆信息
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@RestController
@RequestMapping("/archive/vehicle/info")
public class VehicleInfoController {
    @Autowired
    private VehicleInfoBusinessService vehicleInfoBusinessService;

    /**
     * 查询单条车辆信息
     * @param vehicleNo 车辆编号
     * @return
     */
    @GetMapping(value = "/queryOne")
    public Response<VehicleInfoPageQueryVO> queryOne(String vehicleNo) {
        return Response.succeed(vehicleInfoBusinessService.queryOne(vehicleNo));
    }

    /**
     * 分页查询车辆信息
     *
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/page")
    public Response<PageDTO<VehicleInfoPageQueryVO>> queryPage(@RequestBody VehicleInfoPageQueryDTO queryDTO) {
        return Response.succeed(vehicleInfoBusinessService.queryPage(queryDTO));
    }

    /**
     * 查询车辆型号列表
     *
     * @return
     */
    @GetMapping(value = "/typeList")
    public Response<List<String>> queryTypeList() {
        return Response.succeed(vehicleInfoBusinessService.queryTypeList());
    }

    /**
     * 车辆档案导出（前端接口）
     *
     * @param queryDTO
     * @return
     * @throws Exception
     */
    @CustomExcelExport(
            moduleName = "无人车档案信息",
            fileName = "车辆档案导出",
            dataUrl = "/slv/archive/vehicle/info/exportExcel/data"
    )
    @PostMapping(value = "/exportExcel")
    public Response<?> exportExcel(@RequestBody VehicleInfoPageQueryDTO queryDTO) throws Exception {
        return Response.succeed();
    }

    /**
     * 车辆档案导出（后台接口）
     *
     * @param queryDTO
     * @return
     * @ignore
     */
    @PostMapping(value = "/exportExcel/data")
    public Response<ExportResult<VehicleInfoExportVO>> exportExcelData(@RequestBody VehicleInfoPageQueryDTO queryDTO) {
        PageDTO<VehicleInfoPageQueryVO> pageDTO = vehicleInfoBusinessService.queryPage(queryDTO);
        List<VehicleInfoExportVO> exportVOList = pageDTO.getList().stream().map(vo -> BeanUtil.copyProperties(vo, VehicleInfoExportVO.class)).toList();
        ExportResult<VehicleInfoExportVO> result = ExportResult.wrap(exportVOList, pageDTO.getTotalPage(), VehicleInfoExportVO.class);
        return Response.succeed(result);
    }

    /**
     * 上下电
     * @param vehicleNo 车辆编号
     * @param isOn 是否上电 true - 上电 false - 下电
     */
    @GetMapping(value = "/powerOnOff")
    public Response<?> powerOnOff(@RequestParam(value = "vehicleNo") String vehicleNo,
                                  @RequestParam(value = "isOn") Boolean isOn) {
        return vehicleInfoBusinessService.powerOnOff(vehicleNo, isOn);
    }

    /**
     * 开门
     */
    @GetMapping(value = "/openDoor")
    public Response<?> openDoor(@RequestParam(value = "vehicleNo") String vehicleNo) {
        return vehicleInfoBusinessService.openDoor(vehicleNo);
    }
}
