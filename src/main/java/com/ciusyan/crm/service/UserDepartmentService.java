package com.ciusyan.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciusyan.crm.pojo.po.UserDepartment;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserDepartmentService extends IService<UserDepartment> {

}
