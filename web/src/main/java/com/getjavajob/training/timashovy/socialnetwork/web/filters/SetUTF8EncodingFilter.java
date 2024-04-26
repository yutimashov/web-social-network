package com.getjavajob.training.timashovy.socialnetwork.web.filters;

import javax.servlet.*;
import java.io.IOException;

public class SetUTF8EncodingFilter implements Filter {

    private static final String encoding = "UTF-8";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setContentType("text/html; charset=" + encoding);
        servletResponse.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
