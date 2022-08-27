package com.ciusyan.crm.common.exception;


import com.ciusyan.crm.common.util.JsonVos;
import com.ciusyan.crm.common.util.Streams;
import com.ciusyan.crm.pojo.vo.result.CodeMsg;
import com.ciusyan.crm.pojo.vo.result.JsonVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    // 拦截所有异常。不同异常不同处理
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Throwable.class)
    public JsonVo handle(Throwable t) {
        // 先打印日志信息
        log.error("handle", t);

        if (t instanceof CommonException) {
            return handle((CommonException) t);
        } else if (t instanceof BindException) {
            return handle((BindException) t);
        } else if (t instanceof MethodArgumentNotValidException) {
            return handle((MethodArgumentNotValidException) t);
        } else if (t instanceof ConstraintViolationException) {
            return handle((ConstraintViolationException) t);
        } else if (t instanceof AuthorizationException) {
            return JsonVos.error(CodeMsg.NO_PERMISSION);
        }

        // 处理连环异常
        Throwable cause = t.getCause();
        if (cause != null) {
            return handle(cause);
        }

        // 前面实在处理不了了。只返回一个错误状态码【400】
        return JsonVos.error();
    }

    private JsonVo handle(CommonException ce) {
        return JsonVos.error(ce.getCode(), ce.getMessage());
    }

    private JsonVo handle(BindException be) {
        List<ObjectError> errors = be.getBindingResult().getAllErrors();
        List<String> defaultMsg = Streams.map(errors, ObjectError::getDefaultMessage);
        String msg = StringUtils.collectionToDelimitedString(defaultMsg, ",");
        return JsonVos.error(msg);
    }

    private JsonVo handle(ConstraintViolationException cve) {
        String errorMsg = cve.getMessage();
        String msg = errorMsg.substring(errorMsg.lastIndexOf(".") + 1);
        return JsonVos.error(msg);
    }

    private JsonVo handle(MethodArgumentNotValidException mae) {
        List<String> msgS = Streams.map(mae.getBindingResult().getAllErrors(), ObjectError::getDefaultMessage);
        String msg = StringUtils.collectionToDelimitedString(msgS, ",");
        return JsonVos.error(msg);
    }

}
