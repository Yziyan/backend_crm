package com.ciusyan.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciusyan.crm.pojo.po.Department;
import com.ciusyan.crm.pojo.vo.request.page.DepartmentPageReqVo;
import com.ciusyan.crm.pojo.vo.request.save.DepartmentReqVo;
import com.ciusyan.crm.pojo.vo.response.DepartmentVo;
import com.ciusyan.crm.pojo.vo.result.PageVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface DepartmentService extends IService<Department> {

    // 根据部门id查询部门信息
    List<Department> listDptS(List<Short> dptIds);

    // 通过UserId查询所属部门
    Department getByUserId(Integer userId);

    // 分页查询部门信息
    PageVo<DepartmentVo> list(DepartmentPageReqVo reqVo);

    // 保存 or 编辑
    @Transactional(readOnly = false)
    boolean saveOrUpdate(DepartmentReqVo departmentReqVo);
}
