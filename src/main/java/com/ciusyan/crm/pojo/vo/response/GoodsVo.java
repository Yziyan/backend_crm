package com.ciusyan.crm.pojo.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表(Goods)实体类
 *
 * @author ZhiYan
 * @since 2022-05-06 01:17:30
 */
@Data
@ApiModel("【数据】商品")
public class GoodsVo {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("创建时间")
    private Long createdTime;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("原价")
    private BigDecimal oldPrice;

    @ApiModelProperty("现价")
    private BigDecimal newPrice;

    @ApiModelProperty("描述信息")
    private String description;

    @ApiModelProperty("图片")
    private String imgUrl;

    @ApiModelProperty("上架状态【0：下架；1：下架】")
    private Integer state;

    @ApiModelProperty("库存量")
    private Integer stock;

    @ApiModelProperty("销量")
    private Integer saleCount;

    @ApiModelProperty("点赞数")
    private Integer favorCount;

    @ApiModelProperty("生产地")
    private String address;

    @ApiModelProperty("种类")
    private Short categoryId;

}
