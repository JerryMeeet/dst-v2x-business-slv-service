package com.dst.steed.vds.common.domain.response;

import lombok.Getter;

@Getter
public enum RespType {
    SUCCESS(200, "操作成功"),
    BUSINESS_ERROR(500, "业务异常"),
    SYSTEM_ERROR(500, "系统异常");

    private final int code;
    private final String msg;

    RespType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
