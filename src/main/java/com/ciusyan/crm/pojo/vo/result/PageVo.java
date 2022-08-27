package com.ciusyan.crm.pojo.vo.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("分页查询的数据")
public class PageVo<T> {

    @ApiModelProperty("总页数")
    private Long pages;

    @ApiModelProperty("总数")
    private Long count;

    @ApiModelProperty("分页数据")
    private List<T> data;

}
