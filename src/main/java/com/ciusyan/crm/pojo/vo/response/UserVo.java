package com.ciusyan.crm.pojo.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("【数据】用户")
public class UserVo {

    @ApiModelProperty("用户ID")
    private Integer id;

    @ApiModelProperty("用户名称")
    private String name;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("用户头像")
    private String photo;

    @ApiModelProperty("用户电话")
    private String cellphone;

    @ApiModelProperty("注册时间")
    private Long createdTime;

    @ApiModelProperty("修改时间")
    private Long updateTime;

    @ApiModelProperty("用户相册")
    private String album;

    @ApiModelProperty("是否可用【0：禁用、1：可用】")
    private Short enable;

    @ApiModelProperty("角色ID")
    private List<Short> roleIds;

    @ApiModelProperty("部门ID")
    private Short departmentId;

}
