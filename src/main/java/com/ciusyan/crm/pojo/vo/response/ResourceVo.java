package com.ciusyan.crm.pojo.vo.response;


import com.ciusyan.crm.pojo.po.Resource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("【数据】资源")
public class ResourceVo {

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

    @ApiModelProperty("用户权限")
    private String permission;

    @ApiModelProperty("图标")
    private String icon;

}
