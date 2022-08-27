package com.ciusyan.crm.pojo.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("所有资源树【菜单】")
public class ResourceDto {

    @ApiModelProperty("主键")
    private Short id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("链接地址")
    private String uri;

    @ApiModelProperty("类型【0：目录、1：菜单、2：按钮】")
    private Short type;

    @ApiModelProperty("父级资源Id")
    private Short parentId;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("用户权限")
    private String permission;

    @ApiModelProperty("子资源")
    private List<ResourceDto> children;
}
