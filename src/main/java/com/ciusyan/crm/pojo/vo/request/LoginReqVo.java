package com.ciusyan.crm.pojo.vo.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("登录接口")
public class LoginReqVo {

    @ApiModelProperty(value = "用户名称", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "用户密码", required = true)
    @NotBlank
    private String password;

}
