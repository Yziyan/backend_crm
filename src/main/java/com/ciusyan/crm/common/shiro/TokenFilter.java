package com.ciusyan.crm.common.shiro;

import com.ciusyan.crm.common.cache.Caches;
import com.ciusyan.crm.common.util.JsonVos;
import com.ciusyan.crm.pojo.vo.result.CodeMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class TokenFilter extends AccessControlFilter {

    public static final String HEADER_TOKEN = "Token";


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest,
                                      ServletResponse servletResponse,
                                      Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest,
                                     ServletResponse servletResponse) throws Exception {


        // 取出token
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader(HEADER_TOKEN);


        log.debug("onAccessDenied-----{}", token);
        // 如果没有Token

        if (token == null) {
            JsonVos.raise(CodeMsg.NO_TOKEN);
        }

        // 如果Token 过期了

        if (Caches.getToken(token) == null) {
            return JsonVos.raise(CodeMsg.TOKEN_EXPIRED);
        }

        /*
          来到这里说明Token没有问题【鉴权】
          此login是为了去触发Shiro鉴权的方法
         */
        SecurityUtils.getSubject().login(new Token(token));

        return true;
    }
}
