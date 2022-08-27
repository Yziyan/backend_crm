package com.ciusyan.crm.pojo.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@ApiModel("【数据】部门")
public class DepartmentVo {

    @ApiModelProperty("主键")
    private Short id;

    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("部门领导人")
    private String leader;

    @ApiModelProperty("上级部门ID")
    private Short parentId;

}

