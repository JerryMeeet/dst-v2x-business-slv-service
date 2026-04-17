package dst.v2x.business.slv.service.infrastructure.acl.whiterhino.request;

import lombok.Data;

import java.util.List;

/**
 * 白犀牛-获取车辆信息请参
 *
 * @author: zy
 * @date: 2024/11/8 14:51
 */

@Data
public class WhiteRhinoVehicleInfoReq {

    /**
     * 车辆编号集合
     */
    private List<String> vehicleNoList;
}
