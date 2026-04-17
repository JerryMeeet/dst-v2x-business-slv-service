package com.dst.steed.vds.common.filter;

import com.dst.steed.vds.common.util.DstThreadLocalUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            if (request instanceof HttpServletRequest) {
                DstThreadLocalUtil.setRequest((HttpServletRequest) request);
            }
            chain.doFilter(request, response);
        } finally {
            DstThreadLocalUtil.removeRequest();
        }
    }
}
