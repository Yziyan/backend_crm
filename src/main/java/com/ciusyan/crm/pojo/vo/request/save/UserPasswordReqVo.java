package com.ciusyan.crm.pojo.vo.request.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("修改密码")
public class UserPasswordReqVo {

    @NotNull
    @ApiModelProperty(value = "用户ID", required = true)
    private Integer id;

    @NotBlank
    @ApiModelProperty(value = "用户的旧密码", required = true)
    private String oldPassword;

    @NotBlank
    @ApiModelProperty(value = "用户的新密码", required = true)
    String newPassword;

    private String token;

}
