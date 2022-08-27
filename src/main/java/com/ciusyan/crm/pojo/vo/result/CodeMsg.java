package com.ciusyan.crm.pojo.vo.result;

public enum CodeMsg {


    BAD_REQUEST(400, "请求失败"),
    REMOVE_ERROR(50001, "删除失败"),
    SAVE_ERROR(50002, "保存失败"),

    OPERATE_OK(0, "操作成功"),
    REMOVE_OK(0, "删除成功"),
    SAVE_OK(0, "保存成功"),



    WARNING_NAME(70001, "用户名不存在"),
    WARNING_PASSWORD(70002, "密码错误"),
    WARNING_UNABLE(70002, "账号不可用呕~"),
    NO_FILE(70003, "没有文件数据~"),

    WARNING_OLD_PASSWORD(70003, "旧密码错误"),
    WARNING_OLD_NEW_PASSWORD(70003, "旧密码和新密码不能相同呕~"),
    UPDATE_PASSWORD_OK(70004, "密码修改成功"),
    UPDATE_PASSWORD_ERROR(70005, "密码修改失败"),
    UPDATE_USERINFO_OK(70004, "个人信息修改成功"),
    UPDATE_USERINFO_ERROR(70005, "个人信息修改失败"),

    NO_TOKEN(60001, "没有Token，请登录~"),
    TOKEN_EXPIRED(60002, "Token已过期，请重新登录~"),
    UPLOAD_IMG_ERROR(60003, "图片上传失败"),
    NO_PERMISSION(60004, "没有相应的权限呕~");


    private final int code;
    private final String msg;

    CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }



}
