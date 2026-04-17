package dst.v2x.business.slv.service.infrastructure.biz.dict;

import java.util.HashMap;
import java.util.List;

/**
 * 系统自定义常量
 */
public class DictConsts {

    /**
     * 删除标识状态,0-未删除 ,1-已删除
     */
    public static final String NOT_DELETE = "0";


    /**
     * OSS 存储类型 1-私有 2-公共
     */
    public static final String OSS_STORE_TYPE_PRIVATE = "1";
    public static final String OSS_STORE_TYPE_PUBLIC = "2";

    /**
     * 绑定状态,0-未绑定/解绑,1-绑定,2-已解绑
     */
    public static final String UNBIND = "0";
    public static final String BIND = "1";

    /**
     * 下载状态 downloadStatus 1-请求下载，2-正在下载到ftp服务器'', 3-成功下载到ftp服务器, 4-请求下载失败',
     */
    public static final String DOWNLOAD_STATUS_Request = "1";
    public static final String DOWNLOAD_STATUS_2FTP = "2";
    public static final String DOWNLOAD_STATUS_FTP_SUCCESS = "3";
    public static final String DOWNLOAD_STATUS_FTP_FAIL = "4";

    /**
     * 设备类型 设备类型 1-tbox 2-主动安全3-gps
     */
    public static final String DEVICE_TBOX = "1";
    public static final String DEVICE_ADAS = "2";
    public static final String DEVICE_GPS = "3";
    public static final String DEVICE_AEBS_FCM = "12";
    public static final String DEVICE_AEBS_FRM = "13";
    /**
     * 协议版本 0-国标32960-2016(默认) 1-国标32960-2024
     */
    public static final String DEVICE_PROTOCOL_VERSION_2016 = "0";
    public static final String DEVICE_PROTOCOL_VERSION_2024 = "1";
    public static final String DEVICE_PROTOCOL_VERSION_JTT808_2013 = "10";
    public static final String DEVICE_PROTOCOL_VERSION_JTT808_2019 = "11";
    public static final String DEVICE_PROTOCOL_VERSION_GPS = "21";
    public static final String DEVICE_PROTOCOL_VERSION_2016_CMDS_DICT = "GBT32960-2016-cmds";
    public static final String DEVICE_PROTOCOL_VERSION_2016_FIELDS_DICT = "GBT32960-2016-fields";
    public static final String DEVICE_PROTOCOL_VERSION_JTT808_2013_CMDS_DICT = "JTT808-2013-cmds";
    public static final String DEVICE_PROTOCOL_VERSION_JTT808_2013_FIELDS_DICT = "JTT808-2013-fields";
    public static final String DEVICE_PROTOCOL_VERSION_JTT808_2019_CMDS_DICT = "JTT808-2019-cmds";
    public static final String DEVICE_PROTOCOL_VERSION_JTT808_2019_FIELDS_DICT = "JTT808-2019-fields";
    public static final String DEVICE_PROTOCOL_VERSION_GPS_CMDS_DICT = "GPS-cmds";
    public static final String DEVICE_PROTOCOL_VERSION_GPS_FIELDS_DICT = "GPS-fields";
    /**
     * 实时数据传参类型 1-sn, 2-vin
     */
    public static final String REAL_DATA_TYPE_SN = "1";
    public static final String REAL_DATA_TYPE_VIN = "2";
    /**
     * 确认状态 0-已确认 1-二次确认
     */
    public static final String CONFIRM_STATUS_FIRST = "0";
    public static final String CONFIRM_STATUS_SECOND = "1";

    /**
     * 解析默认版本
     */
    public static final String PARSE_VERSION = "1";
    /**
     * 上传频率 1-10s 2-30s 3-24h
     */
    public static final String UPLOAD_FREQUENCY_10S = "1";
    public static final String UPLOAD_FREQUENCY_30S = "2";
    public static final String UPLOAD_FREQUENCY_24H = "3";

    /**
     * 执行结果 执行成功 执行成功
     */
    public static final String OPERATION_SUCCESS = "执行成功";
    public static final String OPERATION_FAIL = "执行失败";

    /**
     * 指令分类 0-升级指令 1-控制指令 2-配置指令 3-查询指令
     */
    public static final String CMD_CLASS_UPGRADE = "0";
    public static final String CMD_CLASS_CONTROL = "1";
    public static final String CMD_CLASS_CONFIG = "2";
    public static final String CMD_CLASS_QUERY = "3";
    public static final String ADAS_ALARM_APPENDIX_INSTRUCTION_NAME = "9208附件提取指令";

    /**
     * adas视频播放常量
     */
    public static final String TOKEN = "token";
    public static final String VIEW_COUNT = "view_count";
    public static final String EXISTS = "EXISTS";
    public static final String CLOSE_VIDEO = "CLOSE_VIDEO";


    /**
     * 指令类型
     */
    public static final String CMD_TYPE_ADAS_TEXT_CONFIG = "8300";
    public static final String CMD_TYPE_ADAS_QUERY_INSTRUCTION = "8106";

    /**
     * 任务状态 0-创建, 1-进行中, 2:已完成, 99:已终止
     */
    public static final String CMD_STATUS_CREATE = "0";
    public static final String CMD_STATUS_EXECUTE = "1";
    public static final String CMD_STATUS_COMPLETED = "2";
    public static final String CMD_STATUS_CANCEL = "99";
    /**
     * 批次子任务状态0:新建(待执行),1:执行中,2执行成功,3执行失败,99已终止
     */
    public static final String CMD_SUB_TASK_STATUS_CREATE = "0";
    public static final String CMD_SUB_TASK_STATUS_EXECUTE = "1";
    public static final String CMD_SUB_TASK_STATUS_SUCCESS = "2";
    public static final String CMD_SUB_TASK_STATUS_FAIL = "3";
    public static final String CMD_SUB_TASK_STATUS_CANCEL = "99";
    /**
     * 操作类型:1-请求接口内容-2-指令应答内容3-执行结果内容
     */
    public static final String OPERATION_TYPE_REQUEST_CONTENT = "1";
    public static final String OPERATION_TYPE_ANSWER_CONTENT = "2";
    public static final String OPERATION_TYPE_RESULT_CONTENT = "3";
    /**
     * 响应状态:请求成功-请求失败/应答成功-应答失败/执行成功-执行失败
     */
    public static final String RESPONSE_STATUS_REQUEST_SUCCESS = "1";
    public static final String RESPONSE_STATUS_REQUEST_FAIL = "2";
    public static final String RESPONSE_STATUS_ANSWER_SUCCESS = "11";
    public static final String RESPONSE_STATUS_ANSWER_FAIL = "12";
    public static final String RESPONSE_STATUS_EXECUTE_SUCCESS = "21";
    public static final String RESPONSE_STATUS_EXECUTE_FAIL = "22";
    /**
     * 指令应答状态:0未应答1已应答
     */
    public static final String CMD_TASK_STATUS_NOT_ANSWER = "0";
    public static final String CMD_TASK_STATUS_ANSWER = "1";

    /**
     * 子任务平台下发状态0-未下发1-已下发
     */
    public static final String CMD_SUB_TASK_PLATFORM_STATUS_NOT_PUSH = "0";
    public static final String CMD_SUB_TASK_PLATFORM_STATUS_PUSH = "1";
    /**
     * 最新结果描述
     */
    public static final String CMD_SUB_LATEST_REMARKS_DEVICE_NOT_BIND = "车辆未绑定设备!";
    public static final String CMD_SUB_LATEST_REMARKS_PLATFORM_FAIL = "平台下发接口失败!";
    public static final String CMD_SUB_LATEST_REMARKS_PLATFORM_SUCCESS = "平台下发接口成功!";
    public static final String CMD_SUB_LATEST_REMARKS_ANSWER_FAIL = "指令应答失败!";
    public static final String CMD_SUB_LATEST_REMARKS_ANSWER_SUCCESS = "指令应答成功!";
    public static final String CMD_SUB_LATEST_REMARKS_OPERATION_FAIL = "执行失败";
    public static final String CMD_SUB_LATEST_REMARKS_OPERATION_SUCCESS = "执行成功";
    /**
     * 协议默认解释id
     */
    public static final Long TBOX_PARED_ID_DEFAULT = 1L;
    public static final Long GPS_PARED_ID_DEFAULT = 214L;
    public static final Long ADAS_PARED_ID_DEFAULT = 3L;
    public static final String VIDEO = "video";
    /**
     * adas任务类型
     */

    public static final Integer ADAS_INSTRUCTION_FLAG = 8103;
    public static final Integer ADAS_OTA_INSTRUCTION_FLAG = 810501;
    public static final Integer ADAS_VOICE_INSTRUCTION_FLAG = 8300;
    public static final Integer ADAS_QUERY_INSTRUCTION_FLAG = 8106;
    /**
     * 告警进行状态
     */
    public static final String ALARM_PROCESS_STATUS_BEGIN = "进行中";
    public static final String ALARM_PROCESS_STATUS_END = "已结束";

    /**
     * 缓存字典
     * DictMapping注解会通过这个集合, 翻译对应的字典值
     */
    public static HashMap<String, HashMap<String, String>> DICT_MAP = new HashMap<>(512);
    /**
     * 缓存字典
     * 用字典值获取字典key
     */
    public static HashMap<String, HashMap<String, List<String>>> DICT_MAP_REVERSE = new HashMap<>(512);

}
