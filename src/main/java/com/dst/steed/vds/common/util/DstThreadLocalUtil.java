package com.dst.steed.vds.common.util;

import jakarta.servlet.http.HttpServletRequest;

public class DstThreadLocalUtil {
    private static final ThreadLocal<HttpServletRequest> REQUEST_HOLDER = new ThreadLocal<>();

    public static void setRequest(HttpServletRequest request) {
        REQUEST_HOLDER.set(request);
    }

    public static HttpServletRequest getRequest() {
        return REQUEST_HOLDER.get();
    }

    public static void removeRequest() {
        REQUEST_HOLDER.remove();
    }
}
