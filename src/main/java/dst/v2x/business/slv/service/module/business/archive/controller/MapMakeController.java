package dst.v2x.business.slv.service.module.business.archive.controller;

import cn.hutool.core.bean.BeanUtil;
import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.config.excel.CustomExcelExport;
import dst.v2x.business.slv.service.common.domain.acl.excel.ExportResult;
import dst.v2x.business.slv.service.infrastructure.biz.archive.dto.MapMakePageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.MapMakeExportVO;
import dst.v2x.business.slv.service.infrastructure.biz.archive.vo.MapMakePageQueryVO;
import dst.v2x.business.slv.service.module.business.archive.service.MapMakeBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 档案信息-路线
 *
 * @author: pengyunlin
 * @date: 2025/8/22 15:51
 */
@RestController
@RequestMapping("/archive/mapMake")
public class MapMakeController {
    @Autowired
    private MapMakeBusinessService mapMakeBusinessService;

    /**
     * 分页查询路线
     *
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/page")
    public Response<PageDTO<MapMakePageQueryVO>> queryPage(@RequestBody MapMakePageQueryDTO queryDTO) {
        return Response.succeed(mapMakeBusinessService.queryPage(queryDTO));
    }

    /**
     * 路线档案导出（前端接口）
     *
     * @param queryDTO
     * @return
     * @throws Exception
     */
    @CustomExcelExport(
            moduleName = "无人车档案信息",
            fileName = "路线档案导出",
            dataUrl = "/slv/archive/mapMake/exportExcel/data"
    )
    @PostMapping(value = "/exportExcel")
    public Response<?> exportExcel(@RequestBody MapMakePageQueryDTO queryDTO) throws Exception {
        return Response.succeed();
    }

    /**
     * 路线档案导出（后台接口）
     *
     * @param queryDTO
     * @return
     * @ignore
     */
    @PostMapping(value = "/exportExcel/data")
    public Response<ExportResult<MapMakeExportVO>> exportExcelData(@RequestBody MapMakePageQueryDTO queryDTO) {
        PageDTO<MapMakePageQueryVO> pageDTO = mapMakeBusinessService.queryPage(queryDTO);
        List<MapMakeExportVO> exportVOList = pageDTO.getList().stream().map(vo -> BeanUtil.copyProperties(vo, MapMakeExportVO.class)).toList();
        ExportResult<MapMakeExportVO> result = ExportResult.wrap(exportVOList, pageDTO.getTotalPage(), MapMakeExportVO.class);
        return Response.succeed(result);
    }
}
