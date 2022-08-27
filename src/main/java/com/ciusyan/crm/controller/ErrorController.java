package com.ciusyan.crm.controller;


import com.ciusyan.crm.common.filter.ErrorFilter;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api("处理Filter内部产生的异常")
public class ErrorController {


    @RequestMapping(ErrorFilter.ERROR_URI)
    public void handle(HttpServletRequest request) throws  Exception {
        throw (Exception) request.getAttribute(ErrorFilter.ERROR_URI);
    }

}
