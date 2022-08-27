package com.ciusyan.crm.pojo.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("【数据】登录")
public class LoginVo {

    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("登录令牌")
    private String token;

}
