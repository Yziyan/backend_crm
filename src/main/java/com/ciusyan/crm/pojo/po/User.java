package com.ciusyan.crm.pojo.po;

import lombok.Data;

import java.util.Date;

/**
 * 用户表(User)实体类
 *
 * @author ZhiYan
 * @since 2022-04-29 00:11:27
 */


@Data
public class User {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 修改信息的时间
     */
    private Date updateTime;
    /**
     * 用户名
     */
    private String name;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String photo;
    /**
     * 用户相册
     */
    private String album;
    /**
     * 用户电话
     */
    private String cellphone;
    /**
     * 账号状态【1：可以、0：禁用】
     */
    private Short enable;
    /**
     * 部门id
     */
    private Short departmentId;




}

