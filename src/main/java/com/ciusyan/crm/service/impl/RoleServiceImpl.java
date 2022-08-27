package com.ciusyan.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciusyan.crm.common.cache.Caches;
import com.ciusyan.crm.common.enhance.MpLambdaQueryWrapper;
import com.ciusyan.crm.common.enhance.MpPage;
import com.ciusyan.crm.common.mapStructs.MapStructs;
import com.ciusyan.crm.common.util.Streams;
import com.ciusyan.crm.mapper.RoleMapper;
import com.ciusyan.crm.mapper.UserRoleMapper;
import com.ciusyan.crm.pojo.po.Role;
import com.ciusyan.crm.pojo.po.RoleResource;
import com.ciusyan.crm.pojo.po.UserRole;
import com.ciusyan.crm.pojo.vo.request.page.RolePageReqVo;
import com.ciusyan.crm.pojo.vo.request.save.RoleReqVo;
import com.ciusyan.crm.pojo.vo.response.RoleVo;
import com.ciusyan.crm.pojo.vo.result.PageVo;
import com.ciusyan.crm.service.ResourceService;
import com.ciusyan.crm.service.RoleResourceService;
import com.ciusyan.crm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Service
public class RoleServiceImpl
        extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleResourceService roleResourceService;

    /**
     * 根据用户Id获取所有角色Id
     *
     * @param userId : 用户Id
     * @return ： 用户的角色Id
     */
    @Override
    public List<Short> listRoleIds(Integer userId) {
        if (userId == null || userId <= 0) return null;

        MpLambdaQueryWrapper<UserRole> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.select(UserRole::getRoleId).eq(UserRole::getUserId, userId);

        List<Object> roleIds = userRoleMapper.selectObjs(wrapper);

        return Streams.map(roleIds, (roleId) -> ((Integer) roleId).shortValue());

    }

    /**
     * 根据角色ID查询对应角色信息
     * @param roleIds : 用户的所有角色id
     * @return ： 该用户所拥有的所有角色
     */
    @Override
    public List<Role> listRole(List<Short> roleIds) {

        if (roleIds == null || roleIds.size() <= 0) return null;
        return Streams.map(roleIds, (roleId) -> baseMapper.selectById(roleId));
    }

    /**
     * 分页查询角色列表
     * @param query : 查询条件
     * @return : 分页完成的角色列表
     */
    @Override
    public PageVo<RoleVo> list(RolePageReqVo query) {

        // 查询条件
        MpLambdaQueryWrapper<Role> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(query.getName(), Role::getName).
                like(query.getIntro(), Role::getIntro);

        return  baseMapper.
                selectPage(new MpPage<>(query), wrapper).
                buildVo(MapStructs.INSTANCE::po2vo);
    }

    /**
     * 根据用户id删除所有角色Id
     * @param userId：用户id
     * @return ：是否删除成功
     */
    @Override
    public Boolean removeByUserId(Integer userId) {
        // id合法才去查数据库，执行删除的操作
        if (userId == null || userId <= 0) return false;

        MpLambdaQueryWrapper<UserRole> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);

        return userRoleMapper.delete(wrapper) > 0;
    }

    /**
     * 【保存 & 更新】角色信息
     * @param reqVo ：前端传来的【角色信息 + 资源】
     * @return ：是否保存成功
     */
    @Override
    public Boolean saveOrUpdate(RoleReqVo reqVo) {

        // 将reqVo转成Po
        Role Po = MapStructs.INSTANCE.reqVo2po(reqVo);

        // 保存角色的信息
        if (!saveOrUpdate(Po)) return false;

        Short id = reqVo.getId();

        if (id != null && id > 0) {

            // 查出拥有该角色的所有用户
            MpLambdaQueryWrapper<UserRole> wrapper = new MpLambdaQueryWrapper<>();
            wrapper.select(UserRole::getUserId).eq(UserRole::getRoleId, id);
            // 将拥有该角色的用户全部踢下线【必须重新登录】
            Caches.removeByUserIds(userRoleMapper.selectObjs(wrapper));
            // 删除角色所有的资源
            resourceService.removeByRoleId(id);
        }

        // 为角色添加对应资源
        List<Short> resIds = reqVo.getResIds();
        if (CollectionUtils.isEmpty(resIds)) return true; // 说明没有传资源过来

        // 构建批量保存的 【List<RoleResource>】
        List<RoleResource> roleResources = new ArrayList<>();
        for (Short resId : resIds) {
            // 构建 【RoleResource】 对象
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(Po.getId());
            roleResource.setResourceId(resId);
            roleResources.add(roleResource);
        }

        return roleResourceService.saveBatch(roleResources);
    }

    /**
     * / 查询用户的角色信息
     * @param userId：用户ID
     * @return ：用户拥有的角色
     */
    @Override
    public List<Role> listRoleByUserId(Integer userId) {
        if (userId == null || userId <= 0) return null;

        List<Short> roleIds = listRoleIds(userId);
        if (CollectionUtils.isEmpty(roleIds)) return  null;

        // 根据角色Id查询角色信息
        MpLambdaQueryWrapper<Role> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.in(Role::getId, roleIds);

        return baseMapper.selectList(wrapper);
    }

}

