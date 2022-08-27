package com.ciusyan.crm.pojo.vo.request.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("【添加 & 编辑】角色信息")
public class RoleReqVo {

    @ApiModelProperty("大于0是编辑，其余是新增")
    private Short id;

    @ApiModelProperty(value = "角色名称", required = true)
    private String name;

    @ApiModelProperty("角色介绍")
    private String intro;

    @ApiModelProperty("资源id")
    private List<Short> resIds;

}
