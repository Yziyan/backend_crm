package com.ciusyan.crm.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciusyan.crm.common.mapStructs.MapStructs;
import com.ciusyan.crm.common.util.Constants;
import com.ciusyan.crm.common.util.JsonVos;
import com.ciusyan.crm.common.util.Streams;
import com.ciusyan.crm.pojo.po.Role;
import com.ciusyan.crm.pojo.vo.request.page.RolePageReqVo;
import com.ciusyan.crm.pojo.vo.request.save.RoleReqVo;
import com.ciusyan.crm.pojo.vo.response.RoleVo;
import com.ciusyan.crm.pojo.vo.result.CodeMsg;
import com.ciusyan.crm.pojo.vo.result.DataJsonVo;
import com.ciusyan.crm.pojo.vo.result.JsonVo;
import com.ciusyan.crm.pojo.vo.result.PageJsonVo;
import com.ciusyan.crm.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/roles")
@Api(tags = "角色模块")
public class RoleController extends BaseController<Role, RoleReqVo> {

    @Autowired
    private RoleService roleService;


    @PostMapping("/list")
    @ApiOperation("角色列表")
    @RequiresPermissions(Constants.Permission.SYS_ROLE_QUERY)
    public PageJsonVo<RoleVo> list(@RequestBody RolePageReqVo query) {
        return JsonVos.ok(roleService.list(query));
    }

    @GetMapping
    @ApiOperation("所有角色")
    @RequiresPermissions(Constants.Permission.SYS_ROLE_QUERY)
    public DataJsonVo<List<RoleVo>> list() {
        return JsonVos.ok(Streams.map(roleService.list(), MapStructs.INSTANCE::po2vo));
    }

    // 重写父类的 【编辑 & 添加】
    @Override
    @RequiresPermissions(value = {
            Constants.Permission.SYS_ROLE_CREATE,
            Constants.Permission.SYS_ROLE_UPDATE
    }, logical = Logical.OR)
    public JsonVo save(@RequestBody RoleReqVo roleReqVo) {
        if (roleService.saveOrUpdate(roleReqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @Override
    @RequiresPermissions(Constants.Permission.SYS_ROLE_DELETE)
    public JsonVo remove(String ids) {
        return super.remove(ids);
    }

    @Override
    protected IService<Role> getService() {
        return roleService;
    }

    @Override
    protected Function<RoleReqVo, Role> getFunction() {
        return MapStructs.INSTANCE::reqVo2po;
    }


}
