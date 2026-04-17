package dst.v2x.business.slv.service.module.business.datarepair.controller;

import com.dst.steed.vds.common.domain.response.Response;
import dst.v2x.business.slv.service.common.enums.archive.VehicleCompanyEnum;
import dst.v2x.business.slv.service.infrastructure.acl.neolix.service.NeolixRemoteApiService;
import dst.v2x.business.slv.service.module.business.archive.service.VehicleInfoZelosBusinessService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 供应商相关数据修复
 * @author pyl
 * @date 2025/6/24 17:47
 */
@RestController
@RequestMapping("/dataRepair/supplier")
@Slf4j
@Validated
public class DataRepairSupplierController {
    @Autowired
    private NeolixRemoteApiService neolixRemoteApiService;
    @Autowired
    private VehicleInfoZelosBusinessService vehicleInfoZelosBusinessService;

    /**
     * 刷新token
     * @param type VehicleCompanyEnum
     * @author pyl
     * @date 2025/6/24 17:47
     */
    @GetMapping("/flushToken")
    public Response sync(@NotNull(message = "type不能为空") Integer type) {
        if(type.equals(VehicleCompanyEnum.VEHICLE_COMPANY_2.getCode())){
            vehicleInfoZelosBusinessService.flushToken();
        }else if(type.equals(VehicleCompanyEnum.VEHICLE_COMPANY_3.getCode())){
            neolixRemoteApiService.flushToken();
        }
        return Response.succeed();
    }
}
