package com.ciusyan.crm.common.enhance;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.ciusyan.crm.pojo.po.User;

import java.sql.Array;
import java.util.Arrays;
import java.util.Date;

public class MpLambdaQueryWrapper<T> extends LambdaQueryWrapper<T> {

    @SafeVarargs
    public final MpLambdaQueryWrapper<T> like(Object val, SFunction<T, ?>... funcs) {
        // 值如果是空 or 空串 【直接返回】
        // 返回this的目的是为了使用链式编程
        if (val == null) return this;
        String str = val.toString();
        if (str.length() == 0) return this;
        return (MpLambdaQueryWrapper<T>) nested((w) -> {
            // 给 wrapper拼接查询条件。
            for (SFunction<T, ?> func : funcs) {
                w.like(func, str).or();
            }
        });
    }

    @SafeVarargs
    public final MpLambdaQueryWrapper<T> between(Object[] val, SFunction<T, ?> ...funcs) {
        // 值如果是空 or 空串 【直接返回】
        // 返回this的目的是为了使用链式编程

        if (val == null) return this;
        if (val.length <= 0) return this;

        return (MpLambdaQueryWrapper<T>) nested((w) -> {
            // 给 wrapper拼接查询条件。
            for (SFunction<T, ?> func : funcs) {
                w.between(func, val[0], val[1]);
            }
        });
    }


}
