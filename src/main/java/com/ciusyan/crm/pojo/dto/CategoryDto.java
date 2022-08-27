package com.ciusyan.crm.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("所有的种类【商品】")
public class CategoryDto {

    @ApiModelProperty("主键")
    private Short id;

    @ApiModelProperty("种类名称")
    private String name;

    @ApiModelProperty("父级ID")
    private Short parentId;

    @ApiModelProperty("子种类")
    private List<CategoryDto> children;

}
