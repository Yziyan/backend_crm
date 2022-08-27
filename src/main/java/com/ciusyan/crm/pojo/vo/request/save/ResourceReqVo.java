package com.ciusyan.crm.pojo.vo.request.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@ApiModel("【添加 & 编辑】资源信息")
public class ResourceReqVo {


    @ApiModelProperty("主键【id大于零就是编辑、否则为添加】")
    private Short id;

    @NotBlank
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @ApiModelProperty("链接地址")
    private String uri;

    @ApiModelProperty("资源类型【0是目录，1是菜单，2是目录】不传就是0")
    private Short type;

    @ApiModelProperty("用户权限")
    private String permission;

    @ApiModelProperty("父级id【不传就是0】")
    private Short parentId;

    @ApiModelProperty("图标")
    private String icon;

}

