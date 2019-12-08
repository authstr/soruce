package com.f4Blog.basic.reqres.response;

import com.f4Blog.basic.exception.BaseExceptionEnum;

/**
 * 对请求失败 要返回的数据进行封装
 * @author authstr
 * @time 2019年10月11日14:12:23
 */
public class ErrorResponseData extends ResponseData {
    private String exceptionClazz;

    public ErrorResponseData() {
        super(false, BaseExceptionEnum.SERVER_ERROR.getCode(), BaseExceptionEnum.SERVER_ERROR.getMessage(), null);
    }

    public ErrorResponseData(String message) {
        super(false, BaseExceptionEnum.SERVER_ERROR.getCode(), message, null);
    }

    public ErrorResponseData(String code, String message) {
        super(false, code, message, null);
    }

    public ErrorResponseData(String code, String message, Object object) {
        super(false, code, message, object);
    }

    public String getExceptionClazz() {
        return exceptionClazz;
    }

    public void setExceptionClazz(String exceptionClazz) {
        this.exceptionClazz = exceptionClazz;
    }
}
