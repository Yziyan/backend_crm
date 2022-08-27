package com.ciusyan.crm.pojo.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 种类表(Category)实体类
 *
 * @author ZhiYan
 * @since 2022-05-06 01:28:25
 */
@Data
public class Category {

    /**
     * 主键
     */
    private Short id;
    /**
     * 名称
     */
    private String name;
    /**
     * 父级种类
     */
    private Short parentId;

}
