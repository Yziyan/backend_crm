package com.ciusyan.crm.pojo.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@ApiModel("【数据】商品种类")
public class CategoryVo {

    @ApiModelProperty("主键")
    private Short id;

    @ApiModelProperty("种类名称")
    private String name;

    @ApiModelProperty("父级ID【0：一级】")
    private Short parentId;
}
