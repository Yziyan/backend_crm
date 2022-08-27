package com.ciusyan.crm.pojo.vo.request.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("用户修改个人信息")
public class UserInfoReqVo {

    @NotNull
    @ApiModelProperty(value = "用户ID", required = true)
    private Integer id;

    @ApiModelProperty("用户的真实姓名")
    private String nickname;

    @ApiModelProperty("用户电话号码")
    private String cellphone;

}
