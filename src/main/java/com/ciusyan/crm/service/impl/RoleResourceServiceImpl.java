package com.ciusyan.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciusyan.crm.common.enhance.MpLambdaQueryWrapper;
import com.ciusyan.crm.common.util.Streams;
import com.ciusyan.crm.mapper.RoleResourceMapper;
import com.ciusyan.crm.pojo.po.RoleResource;
import com.ciusyan.crm.service.RoleResourceService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class RoleResourceServiceImpl
        extends ServiceImpl<RoleResourceMapper, RoleResource>
        implements RoleResourceService {


    /**
     * / 根据角色Id查询所含资源ID
     * @param roleIds：角色Id
     * @return ： 该角色对应的资源id
     */
    @Override
    public List<Short> resIdsByRoleIds(List<Short> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) return null;


        MpLambdaQueryWrapper<RoleResource> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.select(RoleResource::getResourceId).in(RoleResource::getRoleId, roleIds);
        List<Object> resIds = baseMapper.selectObjs(wrapper);

        // 去掉重复角色
        Set<Object> result = new HashSet<>(resIds);
        return Streams.map(result, (id) -> ((Integer) id).shortValue());

    }

}
