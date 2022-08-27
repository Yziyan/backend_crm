package com.ciusyan.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciusyan.crm.mapper.UserRoleMapper;
import com.ciusyan.crm.pojo.po.UserRole;
import com.ciusyan.crm.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl
        extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
