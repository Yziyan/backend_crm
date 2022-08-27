package com.ciusyan.crm.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciusyan.crm.common.cache.Caches;
import com.ciusyan.crm.common.enhance.MpLambdaQueryWrapper;
import com.ciusyan.crm.common.enhance.MpPage;
import com.ciusyan.crm.common.mapStructs.MapStructs;
import com.ciusyan.crm.common.util.Streams;
import com.ciusyan.crm.mapper.DepartmentMapper;
import com.ciusyan.crm.mapper.UserDepartmentMapper;
import com.ciusyan.crm.mapper.UserMapper;
import com.ciusyan.crm.pojo.po.Department;
import com.ciusyan.crm.pojo.po.User;
import com.ciusyan.crm.pojo.po.UserDepartment;
import com.ciusyan.crm.pojo.vo.request.page.DepartmentPageReqVo;
import com.ciusyan.crm.pojo.vo.request.save.DepartmentReqVo;
import com.ciusyan.crm.pojo.vo.response.DepartmentVo;
import com.ciusyan.crm.pojo.vo.result.PageVo;
import com.ciusyan.crm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class DepartmentServiceImpl
        extends ServiceImpl<DepartmentMapper, Department>
        implements DepartmentService {

    @Autowired
    private UserDepartmentMapper userDptMapper;

    @Autowired
    private UserMapper userMapper;

    public List<Short> listDptIds(Integer userId) {
        if (userId == null || userId <= 0) return null;

        MpLambdaQueryWrapper<UserDepartment> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.select(UserDepartment::getDepartmentId);
        wrapper.eq(UserDepartment::getUserId, userId);


        List<Object> dptIds = userDptMapper.selectObjs(wrapper);

        return Streams.map(dptIds, (dptId) -> ((Integer) dptId).shortValue());
    }

    /**
     * 根据部门id查询部门信息
     * @param dptIds： 用户的部门id
     * @return ： 用户所属部门的信息
     */
    @Override
    public List<Department> listDptS(List<Short> dptIds) {
        if (dptIds.size() <= 0) return null;

        return Streams.map(dptIds, (dptId) -> baseMapper.selectById(dptId));
    }

    /**
     * 通过UserId查询所属部门
     * @param userId ：用户id
     * @return ： 所属部门信息
     */
    @Override
    public Department getByUserId(Integer userId) {
        if (userId == null || userId <= 0) return null;

        Short userDptId = userMapper.selectById(userId).getDepartmentId();

        return baseMapper.selectById(userDptId);

    }

    /**
     * 分页查询部门信息
     * @param reqVo：客户端传来的 【关键字 + 分页信息】
     * @return ：分页完成的部门列表
     */
    @Override
    public PageVo<DepartmentVo> list(DepartmentPageReqVo reqVo) {

        MpLambdaQueryWrapper<Department> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(reqVo.getKeyword(), Department::getName, Department::getLeader);

        return baseMapper.selectPage(new MpPage<>(reqVo), wrapper).
                buildVo(MapStructs.INSTANCE::po2vo);
    }


    /**
     * 保存 or 更新 部门信息
     * @param departmentReqVo ：部门信息
     * @return ：是否成功
     */
    @Override
    public boolean saveOrUpdate(DepartmentReqVo departmentReqVo) {

        Department departmentPo = MapStructs.INSTANCE.reqVo2po(departmentReqVo);
        boolean res = saveOrUpdate(departmentPo);

        Short id = departmentReqVo.getId();
        if (id != null && id > 0) {
            // 将改部门的所有用户全部踢下线【需要重新登录】
            MpLambdaQueryWrapper<User> wrapper = new MpLambdaQueryWrapper<>();
            wrapper.select(User::getId).eq(User::getDepartmentId, id);
            Caches.removeByUserIds(userMapper.selectObjs(wrapper));
        }
        return res;
    }
}
