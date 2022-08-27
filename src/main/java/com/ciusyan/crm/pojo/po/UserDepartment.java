package com.ciusyan.crm.pojo.po;

import lombok.Data;


/**
 * 用户-部门(UserDepartment)实体类
 *
 * @author ZhiYan
 * @since 2022-04-29 17:36:10
 */
@Data
public class UserDepartment {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 部门id
     */
    private Integer departmentId;

}
