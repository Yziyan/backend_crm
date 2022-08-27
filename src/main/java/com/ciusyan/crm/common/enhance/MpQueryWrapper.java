package com.ciusyan.crm.common.enhance;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class MpQueryWrapper<T> extends QueryWrapper<T> {

    public final MpQueryWrapper<T> like(Object val, String... columns) {
        if (val == null) return this;
        String str = val.toString();
        if (str.length() == 0) return this;

        return (MpQueryWrapper<T>) nested((w) -> {
            for (String column : columns) {
                w.like(column, str).or();
            }
        });

    }



}
