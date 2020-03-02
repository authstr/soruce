package com.f4Blog.basic.reqres.utils;

import cn.hutool.core.bean.BeanUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
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
     * 以Map<String, String> 的方式 获取request里的所有属性或参数
     * @return
     */
    public static Map<String, String> getRequestParameters() {
        HashMap<String, String> values = new HashMap<String, String>();
        HttpServletRequest request=getRequest();
        if (request == null) {
            return values;
        } else {
            Enumeration enums = request.getParameterNames();
            while(enums.hasMoreElements()) {
                String paramName = (String)enums.nextElement();
                String paramValue =get(paramName);
                values.put(paramName, paramValue);
            }
            return values;
        }
    }

    /**
     * 获取一个指定key的 属性或者参数
     * 如果是数组,以逗号分隔,合并为一个字符串
     * @param key
     * @return
     */
    public static String get(String key){
        //优先从 属性(Attribute) 中获取值
        Object attributeObject=getRequest().getAttribute(key);
        if(attributeObject==null){
            //否则从 参数(parameter) 获取
            String[] values=getRequest().getParameterValues(key);
            return values!=null&&values.length!=0?String.join(",",values):null;
        }else{
            return attributeObject.toString();
        }
    }

    /**
     * 获取一个指定key的属性或参数,并将获取到的值转换为Integer
     * 转换失败返回null.
     * @param key
     * @return
     */
    public static Integer getInteger(String key){
        String res=get(key);
        try {
            return Integer.valueOf(res);
        }catch (NumberFormatException e){
            return null;
        }
    }


    /**
     * 获取一个指定key的属性或参数,以Array的形式获取
     * @param key
     * @return
     */
    public static String[] getArray(String key){
        Object attributeObject=getRequest().getAttribute(key);
        if(attributeObject==null){
            return getRequest().getParameterValues(key);
        }else{
            if(attributeObject instanceof String){
                return new String[] {attributeObject.toString()};
            }
            return attributeObject instanceof  String[]?(String[])attributeObject:null;
        }
    }

    /**
     * 尝试使用Request的参数或属性数据封装出指定类型的对象
     * 建议属性的类型保持一致,否则可能导致错误的赋值或异常
     * @param clazz 指定的class
     * @param <T>
     * @return
     */
    public static  <T> T getObject(Class<T> clazz){
        try {
            //通过clazz 创建 对象
            T target = clazz.newInstance();
            BeanUtils.populate(target, getRequestParameters());
            return target;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从属性中获取添加过的对象
     * @param clazz
     * @param key
     * @param <T>
     * @return
     */
    public  static  <T> T getAttributeObject(Class<T> clazz,String key){
        return (T)getRequest().getAttribute(key);
    }

    /**
     * 向request里添加属性
     * @param key
     * @param value
     */
    public static void add(String key,String value){
        getRequest().setAttribute(key,value);
    }

    /**
     * 向request里添加属性
     * @param key
     * @param value
     */
    public static void addInteger(String key,Integer value){
        getRequest().setAttribute(key,value);
    }

    /**
     * 向request里添加属性
     * @param key
     * @param value
     */
    public static void addArray(String key,String[] value){
        getRequest().setAttribute(key,value);
    }

    /**
     * 向request里添加属性
     * @param key
     * @param value
     */
    public static void addObject(String key,Object value){
        getRequest().setAttribute(key,value);
    }

    /**
     * 从参数或属性map中获取指定key的值,验证是否有值
     * 不会考虑属性添加的Object
     * @param key
     * @return
     */
    public static boolean hasKeyText(String key) {
        return StringUtils.hasText(get(key))||getArray(key)!=null;
    }


    public static HttpMethod getHttpMethod(HttpServletRequest request) {
        return HttpMethod.resolve(request.getMethod());
    }

    public static HttpMethod getHttpMethod() {
        return getHttpMethod(getRequest());
    }


    public static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        HttpServletRequest request = getRequest();
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

}
