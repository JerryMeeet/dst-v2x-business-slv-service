package dst.v2x.business.slv.service.module.business.can.controller;

import com.dst.steed.vds.common.domain.response.PageDTO;
import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.config.excel.CustomExcelExport;
import dst.v2x.business.slv.service.common.domain.acl.excel.ExportResult;
import dst.v2x.business.slv.service.infrastructure.doris.dto.CanPageQueryDTO;
import dst.v2x.business.slv.service.infrastructure.doris.vo.CanDataExportVO;
import dst.v2x.business.slv.service.infrastructure.doris.vo.CanDataVO;
import dst.v2x.business.slv.service.module.business.can.service.CanDataBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CAN数据
 * @author: pengyunlin
 * @date: 2025/8/14 15:51
 */
@RestController
@RequestMapping("/can")
@Slf4j
public class CanController {
    @Autowired
    private CanDataBusinessService canDataBusinessService;

    /**
     * 查询车辆最新一条CAN数据
     * @param vehicleNo 车辆编号
     */
    @GetMapping(value = "/queryLatest")
    public Response<CanDataVO> queryLatest(String vehicleNo) {
        return Response.succeed(canDataBusinessService.queryLatest(vehicleNo));
    }

    /**
     * 查询无人车CAN历史数据(分页)
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/queryPage")
    public Response<PageDTO<CanDataVO>> queryPage(@Validated @RequestBody CanPageQueryDTO queryDTO) {
        return Response.succeed(canDataBusinessService.queryPage(queryDTO));
    }

    /**
     * CAN历史数据导出（前端接口）
     * @param queryDTO
     * @return
     * @throws Exception
     */
    @CustomExcelExport(
            moduleName = "无人车CAN数据",
            fileName = "历史状态数据导出",
            dataUrl = "/slv/can/exportExcel/data"
    )
    @PostMapping(value = "/exportExcel")
    public Response<?> exportExcel(@RequestBody CanPageQueryDTO queryDTO) throws Exception {
        return Response.succeed();
    }

    /**
     * CAN历史数据导出（后台接口）
     * @ignore
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/exportExcel/data")
    public Response<ExportResult<CanDataExportVO>> exportExcelData(@RequestBody CanPageQueryDTO queryDTO) {
        PageDTO<CanDataVO> pageDTO = canDataBusinessService.queryPage(queryDTO);

        // 转换成导出对象
        List<CanDataExportVO> exportVOList = pageDTO.getList().stream().map(vo -> {
            CanDataExportVO exportVO = new CanDataExportVO();
            exportVO.setVehicleNo(vo.getVehicleNo());
            exportVO.setDataTime(vo.getDataTime());
            if(vo.getAbs() != null){
                exportVO.setWhlSpdFL(vo.getAbs().getWhlSpdFL());
                exportVO.setWhlSpdFR(vo.getAbs().getWhlSpdFR());
                exportVO.setWhlSpdRL(vo.getAbs().getWhlSpdRL());
                exportVO.setWhlSpdRR(vo.getAbs().getWhlSpdRR());
            }
            if(vo.getBms() != null){
                exportVO.setBmsChgPwr(vo.getBms().getBmsChgPwr());
                exportVO.setBmsSelfChkStsName(vo.getBms().getBmsSelfChkStsName());
                exportVO.setBmsHVBatVol(vo.getBms().getBmsHVBatVol());
                exportVO.setBmsHVBatCrnt(vo.getBms().getBmsHVBatCrnt());
                exportVO.setBmsHVDisplaySOH(vo.getBms().getBmsHVDisplaySOH());
                exportVO.setBmsHVBatHighestTem(vo.getBms().getBmsHVBatHighestTem());
                exportVO.setBmsHVBatLowestTem(vo.getBms().getBmsHVBatLowestTem());
                exportVO.setChargeStsName(vo.getBms().getChargeStsName());
                exportVO.setBmsReqHvDownName(vo.getBms().getBmsReqHvDownName());
                exportVO.setHeatRelayStsName(vo.getBms().getHeatRelayStsName());
            }
            if(vo.getPdu() != null){
                exportVO.setParentRelayStsName(vo.getPdu().getParentRelayStsName());
                exportVO.setPrechargeRelayStsName(vo.getPdu().getPrechargeRelayStsName());
                exportVO.setChargeRelayStsName(vo.getPdu().getChargeRelayStsName());
            }
            if(vo.getMcu() != null){
                exportVO.setMcuSpeed(vo.getMcu().getMcuSpeed());
                exportVO.setMcuTorque(vo.getMcu().getMcuTorque());
                exportVO.setMcuMotortemp(vo.getMcu().getMcuMotortemp());
            }
            if(vo.getVcu() != null){
                exportVO.setVehicleSpeed(vo.getVcu().getVehicleSpeed());
                exportVO.setVehicleOdo(vo.getVcu().getVehicleOdo());
                exportVO.setVehicleGearName(vo.getVcu().getVehicleGearName());
                exportVO.setAcceleratorPedalStatus(vo.getVcu().getAcceleratorPedalStatus());
                exportVO.setBrakePedalStatus(vo.getVcu().getBrakePedalStatus());
                exportVO.setBmsKeyOn(vo.getVcu().getBmsKeyOn());
                exportVO.setDriveModeStateName(vo.getVcu().getDriveModeStateName());
            }
            if(vo.getModule() != null){
                exportVO.setVehicleVIN(vo.getModule().getVehicleVIN());
                exportVO.setVehicleVcuVersion(vo.getModule().getVehicleVcuVersion());
                exportVO.setVehicleMcuVersion(vo.getModule().getVehicleMcuVersion());
            }
            if(vo.getPowerSteering() != null){
                exportVO.setBehicleSteeringAngle(vo.getPowerSteering().getBehicleSteeringAngle());
            }
            return exportVO;
        }).toList();
        ExportResult<CanDataExportVO> result = ExportResult.wrap(exportVOList, pageDTO.getTotalPage(), CanDataExportVO.class);
        return Response.succeed(result);
    }
}
