package com.ciusyan.crm.pojo.vo.request.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class KeywordPageReqVo extends PageReqVo {
    @ApiModelProperty("关键字")
    private String keyword;
}
