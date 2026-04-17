package com.dst.steed.vds.common.domain.response;

import lombok.Data;

@Data
public class Response<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> Response<T> succeed() {
        return succeed(null);
    }

    public static <T> Response<T> succeed(T data) {
        Response<T> response = new Response<>();
        response.setCode(RespType.SUCCESS.getCode());
        response.setMsg(RespType.SUCCESS.getMsg());
        response.setData(data);
        return response;
    }

    public static <T> Response<T> error(String msg) {
        Response<T> response = new Response<>();
        response.setCode(RespType.BUSINESS_ERROR.getCode());
        response.setMsg(msg);
        return response;
    }

    public static <T> Response<T> error(int code, String msg) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }
}
