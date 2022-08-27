package com.ciusyan.crm.common.util;

import com.ciusyan.crm.common.exception.CommonException;
import com.ciusyan.crm.pojo.vo.result.PageJsonVo;
import com.ciusyan.crm.pojo.vo.result.PageVo;
import com.ciusyan.crm.pojo.vo.result.CodeMsg;
import com.ciusyan.crm.pojo.vo.result.DataJsonVo;
import com.ciusyan.crm.pojo.vo.result.JsonVo;

public class JsonVos {

    public static JsonVo error(String msg) {
        return new JsonVo( false, msg);
    }

    public static JsonVo error(CodeMsg codeMsg) {
        return new JsonVo(codeMsg);
    }

    public static JsonVo error(int code, String msg) {
        return new JsonVo(code, msg);
    }

    public static JsonVo error() {
        return new JsonVo(false);
    }

    public static JsonVo ok() {
        return new JsonVo(true);
    }

    public static JsonVo ok(int code, String msg) {
        return new JsonVo(code, msg);
    }

    public static JsonVo ok(CodeMsg codeMsg) {
        return new JsonVo(codeMsg);
    }

    public static <T> DataJsonVo<T> ok(T data) {
        return new DataJsonVo<>(data);
    }

    public static <T> DataJsonVo<T> ok(T data, String msg){
        return new DataJsonVo<>(data, msg);
    }

    public static <T>PageJsonVo<T> ok(PageVo<T> pageVo) {
        PageJsonVo<T> pageJsonVo = new PageJsonVo<>();
        pageJsonVo.setData(pageVo.getData());
        pageJsonVo.setCount(pageVo.getCount());
        return pageJsonVo;
    }

    public static <T> T raise(String msg) throws CommonException {
        throw new CommonException(msg);
    }

    public static <T> T raise(CodeMsg codeMsg) throws CommonException {
        throw new CommonException(codeMsg);
    }


}
