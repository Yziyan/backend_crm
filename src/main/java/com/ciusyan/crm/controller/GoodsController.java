package com.ciusyan.crm.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciusyan.crm.common.mapStructs.MapStructs;
import com.ciusyan.crm.common.util.Constants;
import com.ciusyan.crm.common.util.JsonVos;
import com.ciusyan.crm.pojo.po.Goods;
import com.ciusyan.crm.pojo.vo.echart.EtAddressCountVo;
import com.ciusyan.crm.pojo.vo.request.page.GoodsPageReqVo;
import com.ciusyan.crm.pojo.vo.request.save.GoodsReqVo;
import com.ciusyan.crm.pojo.vo.response.GoodsVo;
import com.ciusyan.crm.pojo.vo.result.CodeMsg;
import com.ciusyan.crm.pojo.vo.result.DataJsonVo;
import com.ciusyan.crm.pojo.vo.result.JsonVo;
import com.ciusyan.crm.pojo.vo.result.PageJsonVo;
import com.ciusyan.crm.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/goods")
@Api(tags = "商品模块")
public class GoodsController extends BaseController<Goods, GoodsReqVo> {

    @Autowired
    private GoodsService goodsService;

    @ApiOperation("分页查询商品")
    @PostMapping("/list")
    @RequiresPermissions(Constants.Permission.PRO_GOODS_QUERY)
    public PageJsonVo<GoodsVo> list(@RequestBody GoodsPageReqVo query) {
        return JsonVos.ok(goodsService.list(query));
    }


    @ApiOperation("根据地址统计销量")
    @GetMapping("/listStat")
    public DataJsonVo<List<EtAddressCountVo>> listStat() {
        return JsonVos.ok(goodsService.listAddressStat());
    }

    // 重写保存方法
    @Override
    @RequiresPermissions(value = {
            Constants.Permission.PRO_GOODS_CREATE,
            Constants.Permission.PRO_GOODS_UPDATE
    }, logical = Logical.OR)
    public JsonVo save(GoodsReqVo reqVO) {
        if (goodsService.saveOrUpdate(reqVO)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @PostMapping("/upload")
    @ApiOperation("图片上传")
    @RequiresPermissions(value = {
            Constants.Permission.PRO_GOODS_CREATE,
            Constants.Permission.PRO_GOODS_UPDATE
    }, logical = Logical.OR)
    public JsonVo upload(@RequestPart("file") MultipartFile file) {
        if (goodsService.upload(file)) {
            return JsonVos.ok();
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }

    @Override
    @RequiresPermissions(Constants.Permission.PRO_GOODS_DELETE)
    public JsonVo remove(String ids) {
        return super.remove(ids);
    }

    @Override
    protected IService<Goods> getService() {
        return goodsService;
    }

    @Override
    protected Function<GoodsReqVo, Goods> getFunction() {
        return MapStructs.INSTANCE::reqVo2po;
    }
}
