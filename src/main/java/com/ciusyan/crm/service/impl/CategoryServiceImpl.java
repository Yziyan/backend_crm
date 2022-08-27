package com.ciusyan.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciusyan.crm.common.enhance.MpLambdaQueryWrapper;
import com.ciusyan.crm.common.enhance.MpPage;
import com.ciusyan.crm.common.enhance.MpQueryWrapper;
import com.ciusyan.crm.common.mapStructs.MapStructs;
import com.ciusyan.crm.mapper.CategoryMapper;
import com.ciusyan.crm.pojo.dto.CategoryDto;
import com.ciusyan.crm.pojo.po.Category;
import com.ciusyan.crm.pojo.vo.echart.EtGoodsCountVo;
import com.ciusyan.crm.pojo.vo.request.page.CategoryPageReqVo;
import com.ciusyan.crm.pojo.vo.response.CategoryVo;
import com.ciusyan.crm.pojo.vo.result.PageVo;
import com.ciusyan.crm.service.CategoryService;
import com.ciusyan.crm.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl
        extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {


    @Autowired
    private GoodsService goodsService;

    /**
     * 关键字查询
     * @param reqVo：关键字 + 分页信息
     * @return ：条件返回的分页数据
     */
    @Override
    public PageVo<CategoryVo> list(CategoryPageReqVo reqVo) {

        // 查询条件
        MpLambdaQueryWrapper<Category> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(reqVo.getKeyword(), Category::getName);


        return baseMapper.selectPage(new MpPage<>(reqVo), wrapper).
                buildVo(MapStructs.INSTANCE::po2vo);
    }

    /**
     *
     * @return ：树状结构的种类
     */
    @Override
    public List<CategoryDto> listCategoryTree(String  keyword) {

        MpQueryWrapper<Category> wrapper = new MpQueryWrapper<>();
        wrapper.like(keyword, "p.name", "ch.name");

        return baseMapper.selectCategoryTree(wrapper);
    }

    /**
     * 根据父分类ID 统计该分类下的所有种类的数据
     * @param parentId：父分类ID
     * @return ：统计后的数据
     */
    private EtGoodsCountVo getStatByParentId(Short parentId) {

        // 查询该 父分类 的下的所有分类
        MpLambdaQueryWrapper<Category> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Category::getParentId, parentId);
        List<Category> secondCategory = baseMapper.selectList(wrapper);

        // 统计的数据
        Long goodsCount = 0L;
        Integer goodsSaleCount = 0;
        Integer goodsFavorCount = 0;

        // 给统计的数据赋值
        for (Category category : secondCategory) {
            EtGoodsCountVo countVo = goodsService.getGoodsStatByCatId(category.getId());
            goodsCount += countVo.getGoodsCount();
            goodsSaleCount += countVo.getGoodsSaleCount();
            goodsFavorCount += countVo.getGoodsFavorCount();
        }

        // 构建返回的 EtGoodsCountVo
        EtGoodsCountVo resultCountVo = new EtGoodsCountVo();
        resultCountVo.setGoodsCount(goodsCount);
        resultCountVo.setGoodsSaleCount(goodsSaleCount);
        resultCountVo.setGoodsFavorCount(goodsFavorCount);

        return resultCountVo;
    }


    @Override
    public List<EtGoodsCountVo> listStatGoods() {

        // 所有的一级种类
        MpLambdaQueryWrapper<Category> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Category::getParentId, 0);
        List<Category> parentCategories = baseMapper.selectList(wrapper);
        List<EtGoodsCountVo> resList = new ArrayList<>();

        for (Category parentCategory : parentCategories) {
            // 找出这一父类型的所有商品的统计
            EtGoodsCountVo resultCountVo = getStatByParentId(parentCategory.getId());
            // 返回的 EtGoodsCountVo 附上 ID和 Name 的值
            resultCountVo.setId(parentCategory.getId());
            resultCountVo.setName(parentCategory.getName());
            resList.add(resultCountVo);
        }

        return resList;
    }

}
