package com.ciusyan.crm.pojo.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 商品表(Goods)实体类
 *
 * @author ZhiYan
 * @since 2022-05-06 01:17:30
 */
@Data
public class Goods {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 名称
     */
    private String name;
    /**
     * 原价
     */
    private BigDecimal oldPrice;
    /**
     * 现价
     */
    private BigDecimal newPrice;
    /**
     * 描述信息
     */
    private String description;
    /**
     * 图片
     */
    private String imgUrl;
    /**
     * 上架状态
     */
    private Integer state;
    /**
     * 库存量
     */
    private Integer stock;
    /**
     * 销量
     */
    private Integer saleCount;
    /**
     * 点赞数
     */
    private Integer favorCount;
    /**
     * 生产地
     */
    private String address;
    /**
     * 种类
     */
    private Short categoryId;

}
