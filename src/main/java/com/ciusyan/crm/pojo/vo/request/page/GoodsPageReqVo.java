package com.ciusyan.crm.pojo.vo.request.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsPageReqVo extends PageReqVo {

    @ApiModelProperty("创建时间")
    private Date[] createdTime;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述信息")
    private String description;

    @ApiModelProperty("上架状态【0：下架；1：下架】")
    private Integer state;

    @ApiModelProperty("生产地")
    private String address;

    @ApiModelProperty("种类")
    private Short categoryId;

}
