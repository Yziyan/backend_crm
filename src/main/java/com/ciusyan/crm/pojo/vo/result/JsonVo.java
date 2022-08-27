package com.ciusyan.crm.pojo.vo.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JsonVo {


    private static final int CODE_OK = CodeMsg.OPERATE_OK.getCode();
    private static final int CODE_ERROR = CodeMsg.BAD_REQUEST.getCode();

    @ApiModelProperty("状态码【0成功、其余代表失败】")
    private Integer code;
    @ApiModelProperty("请求消息")
    private String msg;


    public JsonVo() {
        this(true);
    }

    public JsonVo(boolean ok) {
        this(ok, null);
    }

    public JsonVo(boolean ok, String msg) {
        this(ok ? CODE_OK : CODE_ERROR, msg);
    }

    public JsonVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonVo(CodeMsg codeMsg) {
        this(codeMsg.getCode(), codeMsg.getMsg());
    }

}
