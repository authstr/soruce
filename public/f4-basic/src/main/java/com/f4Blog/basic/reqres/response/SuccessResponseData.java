package com.f4Blog.basic.reqres.response;

import com.f4Blog.basic.exception.BaseExceptionEnum;

/**
 * 对请求成功 要返回的数据进行封装
 * @author authstr
 * @time 2019年10月11日14:11:10
 */
public class SuccessResponseData extends ResponseData {
    public SuccessResponseData() {
        super(true, BaseExceptionEnum.SUCCESS.getCode(), BaseExceptionEnum.SUCCESS.getMessage(), (Object)null);
    }
    public SuccessResponseData(Object object) {
        super(true,  BaseExceptionEnum.SUCCESS.getCode(),BaseExceptionEnum.SUCCESS.getMessage(), object);
    }
    public SuccessResponseData(String code, String message, Object object) {
        super(true, code, message, object);
    }
}
