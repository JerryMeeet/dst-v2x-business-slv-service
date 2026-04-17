package dst.v2x.business.slv.service.infrastructure.acl.zelos.convert;

import dst.v2x.business.slv.service.common.enums.raw.VehicleErrorStatusEnum;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 九识转换类
 */
@Getter
public class ZelosErrorStatusConvert {
    private static Map<String, VehicleErrorStatusEnum> zelosErrorStatusMap = new HashMap<>();
    static {
        zelosErrorStatusMap.put("正常", VehicleErrorStatusEnum.ERROR1);
        zelosErrorStatusMap.put("故障", VehicleErrorStatusEnum.ERROR2);
        zelosErrorStatusMap.put("自我修复中", VehicleErrorStatusEnum.ERROR3);
    }

    /**
     * 根据九识的状态名称获取对应的车辆故障状态
     * @param zelosErrorStatusName
     * @return
     */
    public static Integer getVehicleErrorStatusCode(String zelosErrorStatusName) {
        VehicleErrorStatusEnum vehicleErrorStatusEnum = zelosErrorStatusMap.get(zelosErrorStatusName);
        if(vehicleErrorStatusEnum == null){
            return null;
        }
        return vehicleErrorStatusEnum.getCode();
    }
}
