package com.ciusyan.crm.pojo.vo.request.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@ApiModel("【添加 & 编辑】部门信息")
public class DepartmentReqVo {

    @ApiModelProperty("主键:【大于零就是编辑，其余为保存】")
    private Short id;

    @NotBlank
    @ApiModelProperty(value = "部门名称", required = true)
    private String name;

    @NotBlank
    @ApiModelProperty(value = "领导人", required = true)
    private String leader;

    @ApiModelProperty("上级部门ID【不传就是0：没有上级部门】")
    private Short parentId;

}

