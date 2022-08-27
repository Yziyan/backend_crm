package com.ciusyan.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciusyan.crm.pojo.po.Role;
import com.ciusyan.crm.pojo.vo.request.page.RolePageReqVo;
import com.ciusyan.crm.pojo.vo.request.save.RoleReqVo;
import com.ciusyan.crm.pojo.vo.response.RoleVo;
import com.ciusyan.crm.pojo.vo.result.PageVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface RoleService extends IService<Role> {

    // 根据用户Id获取所有角色Id
    List<Short> listRoleIds(Integer userId);

    // 根据角色ID查询对应角色信息
    List<Role> listRole(List<Short> roleIds);

    // 角色列表
    PageVo<RoleVo> list(RolePageReqVo query);

    // 根据用户id删除所有角色Id
    @Transactional(readOnly = false)
    Boolean removeByUserId(Integer userId);

    // 保存 & 编辑
    @Transactional(readOnly = false)
    Boolean saveOrUpdate(RoleReqVo reqVo);


    // 查询用户的角色信息
    List<Role> listRoleByUserId(Integer userId);
}
