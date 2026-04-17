package dst.v2x.business.slv.service.common.constant;

/**
 * @Description: 设备上报数据常量
 * @Author: 张朝
 * @create: 2024-09-19-15:56
 */
public class ReportConstant {

    /**
     * 设备状态（实时数据）0-未激活，1-在线 2-离线
     */
    public static final Integer DEVICE_STATUS_UN_ACTIVE = 0;

    /**
     *设备状态（实时数据）0-未激活，1-在线 2-离线
     */
    public static final Integer DEVICE_STATUS_ONLINE = 1;

    /**
     * 设备状态（实时数据）0-未激活，1-在线 2-离线
     */
    public static final Integer DEVICE_STATUS_OFFLINE = 2;

    /**
     * 数据来源（实时数据）1-地上铁直连，2-平台转发 地上铁直连
     */
    public static final Integer DATA_SOURCE_DST = 1;


    /**
     * 数据来源（实时数据）1-地上铁直连，2-平台转发 平台转发
     */
    public static final Integer DATA_SOURCE_FORWARD = 2;


}
