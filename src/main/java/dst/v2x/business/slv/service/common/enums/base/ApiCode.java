package dst.v2x.business.slv.service.common.enums.base;

import lombok.Getter;

/**
 * 返回的状态码
 */
@Getter
public enum ApiCode {

    SUCCESS("0", "成功"),
    ERROR("-1", "系统繁忙,请稍后再试"),

    GET_REQUEST_REQUIRED("lg_40001", "需要GET请求"),
    POST_REQUEST_REQUIRED("lg_40002", "需要POST请求"),
    MISSING_PARAMETER("lg_40003", "缺少参数"),
    PARAMETER_TYPE_ERROR("lg_40004", "参数类型错误"),
    INVALID_PARAMETERS("lg_40005", "无效的参数"),
    JSON_FORMAT_ERROR("lg_40006", "JSON格式错误"),
    CONTENT_TYPE_INVALID("lg_40007", "请求头Content-Type无效"),
    FILE_FORMAT_ERROR("lg_40008", "文件格式错误"),
    FILE_MAX_UPLOAD_SIZE_LIMIT("lg_40009", "文件上传超过最大限制"),

    FORBIDDEN("lg_40401", "您的登录已经超时, 请重新登录!"),
    UNAUTHORIZED("lg_40403", "您无操作权限"),
    NOT_FOUND("lg_40404", "请求的URL地址不存在"),

    SYSTEM_ERROR("lg_50000", "系统异常, 请稍后再试"),
    GATEWAY_SERVER_ERROR("lg_51000", "请求网关服务异常"),
    CONTRACT_SERVER_ERROR("lg_51001", "请求拟合服务异常"),
    SMS_SERVER_ERROR("lg_51002", "请求SMS消息服务异常"),
    AVENGER_SERVER_ERROR("lg_51003", "请求用户中心服务异常"),
    JINHU_SERVER_ERROR("VDS_JINHU_51005", "金琥平台服务异常"),
    IOT_SERVER_ERROR("VDS_IOT_51006", "IOT平台服务异常"),

    RUICHI_SERVER_ERROR("VDS_RUICHI_51007", "瑞驰平台服务异常"),

    JILI_SERVER_ERROR("VDS_JILI_51008", "吉利平台服务异常"),

    ;

    private final String errcode;
    private final String errmsg;

    ApiCode(String errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

}