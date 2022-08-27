package com.ciusyan.crm.pojo.po;

import lombok.Data;


/**
 * 用户-角色(UserRole)实体类
 *
 * @author ZhiYan
 * @since 2022-04-29 17:36:10
 */
@Data
public class UserRole {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 角色id
     */
    private Short roleId;

}
