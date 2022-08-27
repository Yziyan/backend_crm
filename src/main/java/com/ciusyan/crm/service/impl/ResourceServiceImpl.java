package com.ciusyan.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciusyan.crm.common.enhance.MpLambdaQueryWrapper;
import com.ciusyan.crm.common.mapStructs.MapStructs;
import com.ciusyan.crm.common.util.Constants;
import com.ciusyan.crm.common.util.Streams;
import com.ciusyan.crm.mapper.ResourceMapper;
import com.ciusyan.crm.pojo.dto.ResourceDto;
import com.ciusyan.crm.pojo.po.Resource;
import com.ciusyan.crm.pojo.po.RoleResource;
import com.ciusyan.crm.service.ResourceService;
import com.ciusyan.crm.service.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ResourceServiceImpl
        extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {


    @Autowired
    private RoleResourceService roleResourceService;


    /**
     * / 根据角色Id查询对应的菜单【权限】
     *
     * @param roleStrIds：角色ID
     * @return ： 该角色的菜单
     */
    @Override
    public List<ResourceDto> listMenu(String roleStrIds) {
        if (StringUtils.isEmpty(roleStrIds)) return null;

        // 该角色所有的资源的id
        List<Short> roleIds = Streams.map(Arrays.asList(roleStrIds.split(",")), Short::valueOf);
        List<Short> resIds = roleResourceService.resIdsByRoleIds(roleIds);

        // 根据类型排序的 Resource
        List<Resource> resPos = listOrderByType(resIds);

        return listComTree(resPos);
    }


    /**
     * 返回树结构的资源
     *
     * @return :构造好树结构的结果
     */
    @Override
    public List<ResourceDto> listTree() {

        // 所有资源
        MpLambdaQueryWrapper<Resource> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.orderByAsc(Resource::getType);
        List<Resource> resPos = baseMapper.selectList(wrapper);

        return listComTree(resPos);
    }

    /**
     * 根据角色Id 删除对应资源
     *
     * @param roleId：角色ID
     * @return ：是否删除成功
     */
    @Override
    public boolean removeByRoleId(Short roleId) {
        // 验证ID合法性
        if (roleId == null || roleId <= 0) return false;

        MpLambdaQueryWrapper<RoleResource> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(RoleResource::getRoleId, roleId);

        return roleResourceService.remove(wrapper);
    }


    // 返回树状结构的目录
    private List<ResourceDto> listComTree(List<Resource> resources) {
        if (CollectionUtils.isEmpty(resources)) return null;

        // 返回树状结构的目录
        List<ResourceDto> treeDto = new ArrayList<>();
        // 将 po -> dto 的目录
        Map<Short, ResourceDto> doneDto = new HashMap<>();

        for (Resource rePo : resources) {
            // 将 po -> Dto
            ResourceDto resDto = MapStructs.INSTANCE.po2dto(rePo);
            // 放入doneDto中
            doneDto.put(rePo.getId(), resDto);

            Short type = rePo.getType();
            if (type.equals(Constants.DIR)) { // 说明是目录
                // 直接将目录添加到 返回结果中
                treeDto.add(resDto);
            } else { // 说明是菜单和按钮
                // 取出父目录。并且设置子资源
                ResourceDto parent = doneDto.get(rePo.getParentId());
                List<ResourceDto> children = parent.getChildren();
                if (children == null) {
                    // 将其设置为null 集合
                    parent.setChildren(children = new ArrayList<>());
                }
                children.add(resDto);
            }
        }

        return treeDto;

    }

    // 根据资源id查询资源。并且按类型升序排列
    private List<Resource> listOrderByType(List<Short> resIds) {
        if (CollectionUtils.isEmpty(resIds)) return null;

        List<Resource> resPoS = new ArrayList<>();
        // 将该角色所有资源 的Po 转成dto放入 doneDto
        for (Short resId : resIds) {
            // 查出资源，并且将其放入集合中
            Resource resPo = baseMapper.selectById(resId);
            resPoS.add(resPo);
        }

        /**
         * 将此集合按照类型排序
         * Comparator.comparingInt(Resource::getType)
         * ===
         * new Comparator<Resource>() {
         *             @Override
         *             public int compare(Resource o1, Resource o2) {
         *                 return o1.getType() - o2.getType();
         *             }
         *         }
         */
        resPoS.sort(Comparator.comparingInt(Resource::getType));

        return resPoS;
    }

    /**
     * / 根据角色ID获取所有资源
     * @param roleIds：角色ID
     * @return ：资源ID
     */
    @Override
    public List<Resource> listResByRoleId(List<Short> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) return null;

        // 查询这些角色ID对应的资源【已经去重的】
        List<Short> resIds = roleResourceService.resIdsByRoleIds(roleIds);

        MpLambdaQueryWrapper<Resource> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.in(Resource::getId, resIds);

        return baseMapper.selectList(wrapper);
    }

}
