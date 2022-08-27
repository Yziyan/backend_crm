package com.ciusyan.crm.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciusyan.crm.common.mapStructs.MapStructs;
import com.ciusyan.crm.common.util.Constants;
import com.ciusyan.crm.common.util.JsonVos;
import com.ciusyan.crm.common.util.Streams;
import com.ciusyan.crm.pojo.po.Department;
import com.ciusyan.crm.pojo.vo.request.page.DepartmentPageReqVo;
import com.ciusyan.crm.pojo.vo.request.save.DepartmentReqVo;
import com.ciusyan.crm.pojo.vo.response.DepartmentVo;
import com.ciusyan.crm.pojo.vo.result.CodeMsg;
import com.ciusyan.crm.pojo.vo.result.DataJsonVo;
import com.ciusyan.crm.pojo.vo.result.JsonVo;
import com.ciusyan.crm.pojo.vo.result.PageJsonVo;
import com.ciusyan.crm.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("departments")
@Api(tags = "部门模块")
public class DepartmentController extends BaseController<Department, DepartmentReqVo> {

    @Autowired
    private DepartmentService dptService;

    @GetMapping
    @ApiOperation("所有部门")
    @RequiresPermissions(Constants.Permission.SYS_DEPARTMENT_QUERY)
    public DataJsonVo<List<DepartmentVo>> list() {
        return JsonVos.ok(Streams.map(dptService.list(), MapStructs.INSTANCE::po2vo));
    }

    @PostMapping("/list")
    @ApiOperation("分页查询【部门列表】")
    @RequiresPermissions(Constants.Permission.SYS_DEPARTMENT_QUERY)
    public PageJsonVo<DepartmentVo> list(@RequestBody DepartmentPageReqVo reqVo) {
        return JsonVos.ok(dptService.list(reqVo));
    }

    @Override
    @RequiresPermissions(value = {
            Constants.Permission.SYS_DEPARTMENT_CREATE,
            Constants.Permission.SYS_DEPARTMENT_UPDATE
    }, logical = Logical.OR)
    public JsonVo save(@RequestBody DepartmentReqVo departmentReqVo) {
        if (dptService.saveOrUpdate(departmentReqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @Override
    @RequiresPermissions(Constants.Permission.SYS_DEPARTMENT_DELETE)
    public JsonVo remove(String ids) {
        return super.remove(ids);
    }

    @Override
    protected IService<Department> getService() {
        return dptService;
    }

    @Override
    protected Function<DepartmentReqVo, Department> getFunction() {
        return MapStructs.INSTANCE::reqVo2po;
    }
}
