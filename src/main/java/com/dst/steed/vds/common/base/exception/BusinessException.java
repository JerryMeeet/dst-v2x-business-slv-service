package com.dst.steed.vds.common.base.exception;

import com.dst.steed.vds.common.domain.response.RespType;

public class BusinessException extends RuntimeException {
    private int code;

    public BusinessException(String message) {
        super(message);
        this.code = RespType.BUSINESS_ERROR.getCode();
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(RespType respType, String message) {
        super(message);
        this.code = respType.getCode();
    }

    public BusinessException(String code, String message) {
        super(message);
        try {
            this.code = Integer.parseInt(code);
        } catch (NumberFormatException e) {
            this.code = RespType.BUSINESS_ERROR.getCode();
        }
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.code = RespType.SYSTEM_ERROR.getCode();
    }

    public int getCode() {
        return code;
    }
}
