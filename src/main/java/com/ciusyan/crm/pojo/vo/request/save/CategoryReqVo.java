package com.ciusyan.crm.pojo.vo.request.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@ApiModel("【添加 & 编辑】商品种类")
public class CategoryReqVo {


    @ApiModelProperty("【大于零是编辑，否则为保存】")
    private Short id;

    @NotBlank
    @ApiModelProperty(value = "种类名称", required = true)
    private String name;

    @ApiModelProperty("父级ID【默认为0：一级种类】")
    private Short parentId;

}
