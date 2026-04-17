package dst.v2x.business.slv.service.common.constant;

/**
 * @Description: redis 常量类
 * @Author: 张朝
 */
public class RedisConstant {
    /**
     * TBOX档案:设备号
     */
    public static final String REDIS_TBOX_INFO = "tbox:info:";

    /**
     * GPS档案:设备号
     */
    public static final String REDIS_GPS_INFO = "gps:info:";

    /**
     * redis key
     */
    public static final String REDIS_GPS_BIND = "gps:bind:";

    /**
     * 主动安全档案:设备号
     */
    public static final String REDIS_ADAS_INFO = "adas:info:";

    /**
     * 车辆档案:车架号
     */
    public static final String REDIS_CAR_INFO = "car:info:";

    /**
     * 三方平台档案:平台登录名
     */
    public static final String REDIS_PLATFORM_INFO = "platform:info:";

    /**
     * Tbox绑定关系前缀
     */
    public static final String TBOX_BIND_PREFIX = "tbox:bind:";

    /**
     * redis key
     */
    public static final String ADAS_BIND_PREFIX = "adas:bind:";

    /**
     * 车辆绑定关系前缀
     */
    public static final String CAR_BIND_PREFIX = "car:bind:";
}
