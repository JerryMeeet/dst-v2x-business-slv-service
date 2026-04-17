package dst.v2x.business.slv.service.common.redis;

/**
 * @author: zy
 * @date: 2023/8/16 19:06
 */
public class RedisKeyConstant {
    // 车辆实时数据
    public static final String VEHICLE_REALTIME_DATA = "vehicle:realtime:data";

    // 车辆实时数据-CAN
    public static final String VEHICLE_REALTIME_CAN_DATA = "vehicle:realtime:can-data";

    // 九识无人车的token
    public static final String ZELOS_ACCESS_TOKEN_KEY = "ZELOS:ACCESS:TOKEN";

    // 新石器无人车token
    public static final String NEOLIX_ACCESS_TOKEN_KEY = "neolix:access:token";
}
