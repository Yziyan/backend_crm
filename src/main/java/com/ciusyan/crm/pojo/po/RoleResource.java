package com.ciusyan.crm.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色-资源(RoleResource)实体类
 *
 * @author ZhiYan
 * @since 2022-04-29 17:36:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResource {
    /**
     * 角色id
     */
    private Short roleId;
    /**
     * 资源id
     */
    private Short resourceId;


}
