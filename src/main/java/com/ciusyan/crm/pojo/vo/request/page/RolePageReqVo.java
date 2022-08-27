package com.ciusyan.crm.pojo.vo.request.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RolePageReqVo extends PageReqVo {

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色简介")
    private String intro;

}
