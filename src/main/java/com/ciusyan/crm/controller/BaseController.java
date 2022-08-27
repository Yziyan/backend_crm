package com.ciusyan.crm.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciusyan.crm.common.util.JsonVos;
import com.ciusyan.crm.pojo.vo.result.CodeMsg;
import com.ciusyan.crm.pojo.vo.result.JsonVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.function.Function;

@Validated
public abstract class BaseController<PO, ReqVO> {

    protected abstract IService<PO> getService();

    protected abstract Function<ReqVO, PO> getFunction();

    @PostMapping("/remove")
    @ApiOperation("通过id删除一个或多个【多个Id间使用逗号隔开】")
    public JsonVo remove(@NotBlank(message = "Id不能为空") @RequestParam String ids) {
        if (getService().removeByIds(Arrays.asList(ids.split(",")))) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        } else {
            return JsonVos.error(CodeMsg.REMOVE_ERROR);
        }
    }

    @PostMapping("/save")
    @ApiOperation("保存或编辑【有ID就是编辑、否则是保存】")
    public JsonVo save(@Valid @RequestBody ReqVO reqVO) {

        PO po = getFunction().apply(reqVO);
        if (getService().saveOrUpdate(po)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.error(CodeMsg.SAVE_ERROR);
        }
    }
}
