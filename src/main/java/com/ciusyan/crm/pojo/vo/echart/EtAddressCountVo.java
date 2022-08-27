package com.ciusyan.crm.pojo.vo.echart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("不同城市的销量")
public class EtAddressCountVo {

    @ApiModelProperty("城市名称")
    private String address;

    @ApiModelProperty("该城市的总销量")
    private Integer addressSaleCount;

}
