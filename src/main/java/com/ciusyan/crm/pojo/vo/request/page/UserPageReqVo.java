package com.ciusyan.crm.pojo.vo.request.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageReqVo extends PageReqVo  {

    @ApiModelProperty("创建时间")
    private Date[] createdTime;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("姓名")
    private String nickname;

    @ApiModelProperty("电话号码")
    private String cellphone;

    @ApiModelProperty("用户状态")
    private Short enable;

}
