package com.ciusyan.crm.pojo.vo.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DataJsonVo <T> extends JsonVo{
    private T data;

    DataJsonVo() {}

    public DataJsonVo(T data) {
        this(data, null);
    }

    public DataJsonVo(T data, String msg) {
        super(true, msg);
        this.data = data;
    }




}
