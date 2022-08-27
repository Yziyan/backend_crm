package com.ciusyan.crm.pojo.po;

import lombok.Data;


/**
 * 部门(Department)实体类
 *
 * @author ZhiYan
 * @since 2022-04-29 17:36:10
 */
@Data
public class Department {
    /**
     * 主键
     */
    private Short id;
    /**
     * 名称
     */
    private String name;
    /**
     * 领导人
     */
    private String leader;
    /**
     * 上级部门
     */
    private Short parentId;

}

