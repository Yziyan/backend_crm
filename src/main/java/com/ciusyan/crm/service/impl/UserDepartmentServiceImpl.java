package com.ciusyan.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciusyan.crm.mapper.UserDepartmentMapper;
import com.ciusyan.crm.pojo.po.UserDepartment;
import com.ciusyan.crm.service.UserDepartmentService;
import org.springframework.stereotype.Service;

@Service
public class UserDepartmentServiceImpl
        extends ServiceImpl<UserDepartmentMapper, UserDepartment>
        implements UserDepartmentService {

}
