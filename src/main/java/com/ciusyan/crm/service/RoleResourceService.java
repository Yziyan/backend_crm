package com.ciusyan.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciusyan.crm.pojo.po.RoleResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface RoleResourceService extends IService<RoleResource> {
    // 根据角色Id查询所含资源资源
    List<Short> resIdsByRoleIds(List<Short> roleIds);
}
