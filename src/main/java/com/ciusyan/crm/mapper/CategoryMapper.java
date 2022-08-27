package com.ciusyan.crm.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ciusyan.crm.pojo.dto.CategoryDto;
import com.ciusyan.crm.pojo.po.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    // 返回树状结构的种类
    List<CategoryDto> selectCategoryTree(@Param(Constants.WRAPPER) Wrapper<Category> wrapper);
}
