package com.ciusyan.crm.pojo.vo.echart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("每个分类商品的个数")
public class EtGoodsCountVo {

    @ApiModelProperty("分类的ID")
    private Short id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("该分类的商品总数量")
    private Long goodsCount;

    @ApiModelProperty("该分类商品的总销量")
    private Integer goodsSaleCount;

    @ApiModelProperty("该分类总收藏数")
    private Integer goodsFavorCount;

}
