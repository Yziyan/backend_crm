package com.ciusyan.crm.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 将没有到达 Controller时出现的异常转交给Controller处理
public class ErrorFilter implements Filter {

    public static final String ERROR_URI = "/handleError";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            request.setAttribute(ERROR_URI, e);
            request.getRequestDispatcher(ERROR_URI).forward(request, response);
        }

    }
}
