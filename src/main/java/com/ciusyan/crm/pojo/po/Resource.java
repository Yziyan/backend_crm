package com.ciusyan.crm.pojo.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 资源(Resource)实体类
 *
 * @author ZhiYan
 * @since 2022-04-30 00:08:19
 */
@Data
public class Resource {
    /**
     * 主键
     */
    private Short id;
    /**
     * 名称
     */
    private String name;
    /**
     * 链接地址
     */
    private String uri;
    /**
     * 权限标识
     */
    private String permission;
    /**
     * 资源类型【0是目录，1是菜单，2是目录】
     */
    private Short type;
    /**
     * 序号
     */
    private Integer sn;
    /**
     * 父级id
     */
    private Short parentId;
    /**
     * 图标
     */
    private String icon;



}

