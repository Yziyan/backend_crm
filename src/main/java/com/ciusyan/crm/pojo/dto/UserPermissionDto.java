package com.ciusyan.crm.pojo.dto;


import com.ciusyan.crm.pojo.po.Resource;
import com.ciusyan.crm.pojo.po.Role;
import com.ciusyan.crm.pojo.po.User;
import lombok.Data;

import java.util.List;

// 用于鉴权的类 【用户信息 + 角色信息 + 权限信息（资源）】
@Data
public class UserPermissionDto {
    // 用户信息
    private User user;
    // 角色信息
    private List<Role> roles;
    // 资源信息
    private List<Resource> resources;
}
