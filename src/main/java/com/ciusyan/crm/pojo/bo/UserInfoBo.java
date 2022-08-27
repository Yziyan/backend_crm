package com.ciusyan.crm.pojo.bo;

import com.ciusyan.crm.pojo.po.Department;
import com.ciusyan.crm.pojo.po.Role;
import com.ciusyan.crm.pojo.po.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoBo {

    @ApiModelProperty("用户个人信息")
    private User user;

    @ApiModelProperty("用户角色信息")
    private List<Role> roles;

    @ApiModelProperty("用户部门信息")
    private Department department;

}
