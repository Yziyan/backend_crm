package com.ciusyan.crm.pojo.vo.request.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表(Goods)实体类
 *
 * @author ZhiYan
 * @since 2022-05-06 01:17:30
 */
@Data
@ApiModel("【添加 & 编辑】商品信息")
public class GoodsReqVo {

    @ApiModelProperty("主键【大于零是编辑，否则为新增】")
    private Integer id;

    @NotBlank
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @NotNull
    @ApiModelProperty("原价")
    private BigDecimal oldPrice;

    @ApiModelProperty("描述信息")
    private String description;

    @ApiModelProperty("上架状态【0：下架；1：下架】不传就是1")
    private Integer state;

    @NotNull
    @ApiModelProperty(value = "库存量", required = true)
    private Integer stock;

    @ApiModelProperty("生产地")
    private String address;

    @NotNull
    @ApiModelProperty(value = "种类", required = true)
    private Short categoryId;

    @ApiModelProperty("图片数据")
    private MultipartFile imgFile;

    @ApiModelProperty("原图片地址")
    private String imgUrl;

}
