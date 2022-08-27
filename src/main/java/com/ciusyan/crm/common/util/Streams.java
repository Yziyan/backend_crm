package com.ciusyan.crm.common.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Streams {

    public static <R, T> List<R> map(Collection<T> list, Function<T, R> function) {
        // 将List<T> -> List<R> 【转换的方式交给function】
        return list.stream().map(function).collect(Collectors.toList());
    }

}
