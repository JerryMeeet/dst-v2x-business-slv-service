package dst.v2x.business.slv.service.infrastructure.acl.zelos.request;

import lombok.Data;

import java.util.List;

/**
 * 请求车辆列表入参
 *
 * @author 江民来
 * @date 2025年02月10日 16:25
 */
@Data
public class ZelosGetAutoVehiclertInfoReq {
    /**
     * 车辆编号集合
     */
    private List<String> vehicleNoList;
}

