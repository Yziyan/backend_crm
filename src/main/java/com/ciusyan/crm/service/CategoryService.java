package com.ciusyan.crm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciusyan.crm.pojo.dto.CategoryDto;
import com.ciusyan.crm.pojo.po.Category;
import com.ciusyan.crm.pojo.vo.echart.EtGoodsCountVo;
import com.ciusyan.crm.pojo.vo.request.page.CategoryPageReqVo;
import com.ciusyan.crm.pojo.vo.response.CategoryVo;
import com.ciusyan.crm.pojo.vo.result.PageVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CategoryService extends IService<Category> {

    // 关键字查询
    PageVo<CategoryVo> list(CategoryPageReqVo reqVo);

    // 树状结构的种类
    List<CategoryDto> listCategoryTree(String keyword);

    List<EtGoodsCountVo> listStatGoods();
}
