package com.f4Blog.basic.reqres.utils;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取request和response对象
 */
public class WebUtils {

    /**
     * 获取请求ip
     * @return
     */
    public static String getIp() {
        HttpServletRequest request = getRequest();
        return request == null ? "127.0.0.1" : request.getRemoteHost();
    }

    /** 获取request对象 **/
    public static HttpServletRequest getRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null){
            return null;
        }
        return ((ServletRequestAttributes)requestAttributes).getRequest();
    }

    /** 获取response对象 **/
    public static HttpServletResponse getResponse(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null){
            return null;
        }
        return ((ServletRequestAttributes)requestAttributes).getResponse();
    }

    /**
     * 以Map<String, String> 的方式 获取request里的所有参数
     * @return
     */
    public static Map<String, String> getRequestParameters( HttpServletRequest request) {
        HashMap<String, String> values = new HashMap<String, String>();
        if (request == null) {
            return values;
        } else {
            Enumeration enums = request.getParameterNames();
            while(enums.hasMoreElements()) {
                String paramName = (String)enums.nextElement();
                String paramValue = request.getParameter(paramName);
                values.put(paramName, paramValue);
            }
            return values;
        }
    }

    public static Map<String, String> getRequestParameters(){
        return getRequestParameters(getRequest());
    }


    public static HttpHeaders getHttpHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (request == null) {
            return headers;
        } else {
            Enumeration enums = request.getHeaderNames();
            while(enums.hasMoreElements()) {
                String paramName = (String)enums.nextElement();
                String paramValue = request.getHeader(paramName);
                headers.add(paramName, paramValue);
            }
            return headers;
        }
    }

    public static HttpHeaders getHttpHeaders() {
        return getHttpHeaders(getRequest());
    }


    public static HttpMethod getHttpMethod(HttpServletRequest request) {
        return HttpMethod.resolve(request.getMethod());
    }

    public static HttpMethod getHttpMetho() {
        return getHttpMethod(getRequest());
    }


//    public static <T> HttpEntity getHttpEntity(T object) {
//
//        HttpHeaders headers = getHttpHeaders();
////        BeanUtil.beanToMap(object);
//        return new HttpEntity(object,headers);
//    }
//
//    public static  HttpEntity getHttpEntity() {
//        HttpHeaders headers = getHttpHeaders();
//        return new HttpEntity(getRequestParameters(),headers);
//    }


}
