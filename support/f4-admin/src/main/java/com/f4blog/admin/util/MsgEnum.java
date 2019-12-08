package com.f4blog.admin.util;

import com.f4Blog.basic.exception.BaseExceptionEnum;
import com.f4Blog.basic.exception.ExceptionEnumInterface;

public enum MsgEnum implements ExceptionEnumInterface {
    USER_EXIST("301","用户已存在"),
    ENCRYPT_ERROR("311","加密出错!");

    private String code;
    private String message;

    MsgEnum(String code, String message){
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
