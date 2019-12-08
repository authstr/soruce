package com.f4Blog.basic.exception;


public enum BaseExceptionEnum implements ExceptionEnumInterface {

    SUCCESS("0","操作成功"),
    SERVER_ERROR("500", "服务器异常"),
    PARA_ERROR("400", "请求参数错误"),
    UNKNOWN_ERROR("-1", "未知异常");

    private String code;
    private String message;
    BaseExceptionEnum(String code, String message){
        this.code=code;
        this.message=message;
    }


    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
