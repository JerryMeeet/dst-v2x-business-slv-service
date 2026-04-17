package dst.v2x.business.slv.service.infrastructure.acl.whiterhino.request;

import lombok.Data;

import java.util.List;

/**
 * 白犀牛-获取车辆实时数据请参
 *
 * @author: zy
 * @date: 2024/11/8 15:04
 */
@Data
public class WhiteRhinoVehicleRealtimeReq {

    /**
     * 车辆编号集合
     */
    private List<String> vehicleNoIds;
}
