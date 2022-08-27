package com.ciusyan.crm.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciusyan.crm.common.mapStructs.MapStructs;
import com.ciusyan.crm.common.util.Constants;
import com.ciusyan.crm.common.util.JsonVos;
import com.ciusyan.crm.pojo.dto.ResourceDto;
import com.ciusyan.crm.pojo.po.Resource;
import com.ciusyan.crm.pojo.vo.request.save.ResourceReqVo;
import com.ciusyan.crm.pojo.vo.result.DataJsonVo;
import com.ciusyan.crm.pojo.vo.result.JsonVo;
import com.ciusyan.crm.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.function.Function;

@RestController
@Api(tags = "资源模块")
@RequestMapping("/resources")
public class ResourceController extends BaseController<Resource, ResourceReqVo> {

    @Autowired
    private ResourceService resourceService;

    @ApiOperation("根据角色Id查询角色菜单")
    @GetMapping("/menu")
    public DataJsonVo<List<ResourceDto>> listMenu(
            @NotNull @ApiParam("角色ID【多个ID用逗号拼接】")
            String roleIds) {
        return JsonVos.ok(resourceService.listMenu(roleIds), "菜单获取成功");
    }

    @ApiOperation("获取树状结构的资源")
    @GetMapping("/listTree")
    public DataJsonVo<List<ResourceDto>> listRseTree() {
        return JsonVos.ok(resourceService.listTree(), "获取目录成功");
    }


    @Override
    @RequiresPermissions(value = {
            Constants.Permission.SYS_MENU_CREATE,
            Constants.Permission.SYS_MENU_UPDATE
    }, logical = Logical.AND)
    public JsonVo save(ResourceReqVo resourceReqVo) {
        return super.save(resourceReqVo);
    }

    @Override
    @RequiresPermissions(Constants.Permission.SYS_MENU_DELETE)
    public JsonVo remove(String ids) {
        return super.remove(ids);
    }

    @Override
    protected IService<Resource> getService() {
        return resourceService;
    }

    @Override
    protected Function<ResourceReqVo, Resource> getFunction() {
        return MapStructs.INSTANCE::reqVo2po;
    }

}
