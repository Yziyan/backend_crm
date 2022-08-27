package com.ciusyan.crm.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciusyan.crm.common.mapStructs.MapStructs;
import com.ciusyan.crm.common.util.Constants;
import com.ciusyan.crm.common.util.JsonVos;
import com.ciusyan.crm.common.util.Streams;
import com.ciusyan.crm.pojo.dto.CategoryDto;
import com.ciusyan.crm.pojo.po.Category;
import com.ciusyan.crm.pojo.vo.echart.EtGoodsCountVo;
import com.ciusyan.crm.pojo.vo.request.page.CategoryPageReqVo;
import com.ciusyan.crm.pojo.vo.request.save.CategoryReqVo;
import com.ciusyan.crm.pojo.vo.response.CategoryVo;
import com.ciusyan.crm.pojo.vo.result.DataJsonVo;
import com.ciusyan.crm.pojo.vo.result.JsonVo;
import com.ciusyan.crm.pojo.vo.result.PageJsonVo;
import com.ciusyan.crm.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/categories")
@Api(tags = "商品种类模块")
public class CategoryController extends BaseController<Category, CategoryReqVo> {


    @Autowired
    private CategoryService categoryService;


    @ApiOperation("分页查询")
    @PostMapping("/list")
    @RequiresPermissions(Constants.Permission.PRO_CATEGORY_QUERY)
    public PageJsonVo<CategoryVo> list(@RequestBody CategoryPageReqVo reqVo) {
        return JsonVos.ok(categoryService.list(reqVo));
    }

    @ApiOperation("所有种类")
    @GetMapping
    @RequiresPermissions(Constants.Permission.PRO_CATEGORY_QUERY)
    public DataJsonVo<List<CategoryVo>> list() {
        return JsonVos.ok(Streams.map(categoryService.list(), MapStructs.INSTANCE::po2vo));
    }

    @ApiOperation("查询树结构的种类")
    @GetMapping("/listTree")
    @RequiresPermissions(value = {
            Constants.Permission.PRO_CATEGORY_CREATE,
            Constants.Permission.PRO_CATEGORY_UPDATE
    }, logical = Logical.OR)
    public DataJsonVo<List<CategoryDto>> listTree(String keyword) {
        return JsonVos.ok(categoryService.listCategoryTree(keyword));
    }

    @ApiOperation("统计所有一级分类")
    @GetMapping("listStat")
    @RequiresPermissions(value = {
            Constants.Permission.PRO_CATEGORY_CREATE,
            Constants.Permission.PRO_CATEGORY_UPDATE
    }, logical = Logical.OR)
    public DataJsonVo<List<EtGoodsCountVo>> listStat() {
        return JsonVos.ok(categoryService.listStatGoods());
    }

    @Override
    @RequiresPermissions(value = {
            Constants.Permission.PRO_CATEGORY_CREATE,
            Constants.Permission.PRO_CATEGORY_UPDATE
    }, logical = Logical.OR)
    public JsonVo save(CategoryReqVo vo) {
        return super.save(vo);
    }

    @Override
    @RequiresPermissions(Constants.Permission.PRO_CATEGORY_DELETE)
    public JsonVo remove(String ids) {
        return super.remove(ids);
    }

    @Override
    protected IService<Category> getService() {
        return categoryService;
    }

    @Override
    protected Function<CategoryReqVo, Category> getFunction() {
        return MapStructs.INSTANCE::reqVo2po;
    }
}
