package com.ciusyan.crm.pojo.vo.request.save;


import com.ciusyan.crm.common.validate.BoolNumber;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("【添加 & 编辑】用户信息")
public class UserReqVo {

    @ApiModelProperty("用户Id【大于0是编辑，否则是保存】")
    private Integer id;

    @NotBlank
    @ApiModelProperty(value = "用户名", required = true)
    private String name;

    @ApiModelProperty("密码【如果为空，则不修改密码】")
    private String password;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("电话号码")
    private String cellphone;

    @BoolNumber
    @ApiModelProperty("账号状态【1：可用、0：禁用】")
    private Short enable;

    @ApiModelProperty("所属部门id")
    private Short departmentId;

    @ApiModelProperty("角色Id【多个角色之间用逗号隔开】")
    private String roleIds;

}
