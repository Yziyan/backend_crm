package com.ciusyan.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciusyan.crm.pojo.dto.ResourceDto;
import com.ciusyan.crm.pojo.po.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ResourceService extends IService<Resource> {

    // 根据角色Id查询对应的菜单【权限】
    List<ResourceDto> listMenu(String roleIds);

    // 返回树结构的资源
    List<ResourceDto> listTree();

    // 根据角色Id 删除对应资源
    boolean removeByRoleId(Short roleId);

    // 根据角色ID获取所有资源
    List<Resource> listResByRoleId(List<Short> roleIds);
}
