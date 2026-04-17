package dst.v2x.business.slv.service.module.business.archive.controller;

import cn.hutool.core.bean.BeanUtil;
import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.config.excel.CustomExcelExport;
import dst.v2x.business.slv.service.common.domain.acl.excel.ExportResult;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.StationInfoPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.entity.StationInfo;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.StationInfoExportVO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.StationInfoPageQueryVO;
import dst.v2x.business.slv.service.module.business.archive.service.StationInfoBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 档案信息-站点信息
 *
 * @author: pengyunlin
 * @date: 2025/6/6 15:51
 */
@RestController
@RequestMapping("/archive/station/info")
public class StationInfoController {
    @Autowired
    private StationInfoBusinessService stationInfoBusinessService;

    /**
     * 分页查询站点信息
     *
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/page")
    public Response<PageDTO<StationInfoPageQueryVO>> queryPage(@RequestBody StationInfoPageQueryDTO queryDTO) {
        return Response.succeed(stationInfoBusinessService.queryPage(queryDTO));
    }

    /**
     * 站点档案导出（前端接口）
     *
     * @param queryDTO
     * @return
     * @throws Exception
     */
    @CustomExcelExport(
            moduleName = "无人车档案信息",
            fileName = "站点档案导出",
            dataUrl = "/slv/archive/station/info/exportExcel/data"
    )
    @PostMapping(value = "/exportExcel")
    public Response<?> exportExcel(@RequestBody StationInfoPageQueryDTO queryDTO) throws Exception {
        return Response.succeed();
    }

    /**
     * 站点档案导出（后台接口）
     *
     * @param queryDTO
     * @return
     * @ignore
     */
    @PostMapping(value = "/exportExcel/data")
    public Response<ExportResult<StationInfoExportVO>> exportExcelData(@RequestBody StationInfoPageQueryDTO queryDTO) {
        PageDTO<StationInfoPageQueryVO> pageDTO = stationInfoBusinessService.queryPage(queryDTO);
        List<StationInfoExportVO> exportVOList = pageDTO.getList().stream().map(vo -> BeanUtil.copyProperties(vo, StationInfoExportVO.class)).toList();
        ExportResult<StationInfoExportVO> result = ExportResult.wrap(exportVOList, pageDTO.getTotalPage(), StationInfoExportVO.class);
        return Response.succeed(result);
    }

    /**
     * 获取站点信息列表-实时
     * @param vehicleNo 车辆编码
     */
    @GetMapping(value = "/getStationInfosByRealtime")
    public Response<List<StationInfo>> getStationInfosByRealtime(String vehicleNo) {
        return stationInfoBusinessService.getStationInfosByRealtime(vehicleNo);
    }

}
