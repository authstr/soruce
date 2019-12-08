package com.f4Blog.basic.web.controller;

/**
 * 控制层要用的常量
 * @author authstr
 *
 */
public class ControllerConstant {
    public static String CODE = "code";
    public static String MSG = "msg";
    public static String DATA = "data";
    private Integer code;
    private Object data;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
