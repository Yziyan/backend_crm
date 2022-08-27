package com.ciusyan.crm.pojo.dto;

import com.ciusyan.crm.pojo.po.Department;
import com.ciusyan.crm.pojo.po.Role;
import com.ciusyan.crm.pojo.vo.response.UserVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("【用户个人信息、角色信息、部门信息】")
public class UserInfoDto {

    @ApiModelProperty("删减过后的User")
    private UserVo user;

    @ApiModelProperty("用户角色信息")
    private List<Role> roles;

    @ApiModelProperty("用户部门信息")
    private Department department;

}
