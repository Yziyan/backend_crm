package com.ciusyan.crm.pojo.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("【数据】角色")
public class RoleVo {

    @ApiModelProperty("角色ID")
    private Short id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色介绍")
    private String intro;

}
