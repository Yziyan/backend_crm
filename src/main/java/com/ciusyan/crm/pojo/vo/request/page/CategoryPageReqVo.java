package com.ciusyan.crm.pojo.vo.request.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

// 商品分类关键字查询
@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryPageReqVo extends KeywordPageReqVo {

}
