package com.ciusyan.crm.pojo.po;

import lombok.Data;


/**
 * 角色表(Role)实体类
 *
 * @author ZhiYan
 * @since 2022-04-29 17:36:10
 */
@Data
public class Role {
    /**
     * 主键
     */
    private Short id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 简介
     */
    private String intro;

}
