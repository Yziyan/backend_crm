package com.ciusyan.crm.pojo.vo.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class PageJsonVo<T> extends DataJsonVo<List<T>> {

    @ApiModelProperty("总记录数")
    private Long count;

}
