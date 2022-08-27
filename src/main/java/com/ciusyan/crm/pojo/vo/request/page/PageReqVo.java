package com.ciusyan.crm.pojo.vo.request.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分页查询")
public class PageReqVo {

    private static final int DEFAULT_SIZE = 10;

    @ApiModelProperty("页码【不传就是第一页】")
    private long page;

    @ApiModelProperty("每页的大小【不传就是10】")
    private long size;

    public long getPage() {
        return Math.max(1, this.page);
    }

    public long getSize() {
        return this.size < 1 ? DEFAULT_SIZE : this.size;
    }
}
