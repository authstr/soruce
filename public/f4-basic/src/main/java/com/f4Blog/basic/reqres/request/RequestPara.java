package com.f4Blog.basic.reqres.request;


import com.f4Blog.basic.reqres.utils.WebUntil;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 将reques的请求参数进行封装,方便传递与调用 v1.3
 * v1.3 增加Integer的验证,增加同时从Integer映射和String映射判断和获取参数功能
 * @author authstr
 * @time 2019年10月11日14:24:18
 */
public class RequestPara {
    private HttpServletRequest request =null;

    //储存request里的参数,数组转String
    private Map<String,String> parameter=null;

    //储存Integer类型的参数
    private Map<String,Integer> parameterInteger=new HashMap<>();

    //储存String[]类型的参数,默认为request里的数据
    private Map<String,String[]> parameterArray=new HashMap<>();

    //储存其他参数
    private Map<String,Object> parameterObject=new HashMap<String,Object>();


    public static RequestPara instance(){
        RequestPara para=new RequestPara(WebUntil.getRequest());
        return para;
    }


    //有参初始化 从request获取map
    public RequestPara(HttpServletRequest request){
        if(request!=null){
            setRequest(request);
        }else{
            parameter=new HashMap<String,String>();
        }
    }
    //无参初始化 new一个map
    public RequestPara(){ parameter=new HashMap<String,String>();}

    public HttpServletRequest getRequest() {
        return request;
    }

    //设置request对象
    public void setRequest(HttpServletRequest request) {

        this.request = request;
        //复制map
        parameterArray.putAll(request.getParameterMap());
        //转换为非数组
        parameter=mapTypeTrans(request.getParameterMap());
    }

    /**
     * 将Map<String,String[]>转换为Map<String,String> 类型
     * @param map
     * @return
     */
    public Map<String,String> mapTypeTrans(Map<String,String[]> map ){
        Map<String,String> res=new HashMap<String,String>();
        for (Map.Entry<String,String[]> temp: map.entrySet()){
            res.put(temp.getKey(),arrayToString(temp.getValue()));
        }
        return res;
    }

    /**
     * 将String数组中转换为 String
     * @param array
     * @return
     */
    public String  arrayToString(String[] array){
        StringBuffer res =new StringBuffer();
        if(array==null){
            return null;
        }
        for(int i=0;i<array.length;i++){
            res.append(array[i]);
            if((array.length-1)!=i){
                res.append(",");
            }
        }
        return res.toString();
    }

    /**
     * 获取一个指定key的String类型参数
     * @param key
     * @return
     */
    public String get(String key){
        return parameter.get(key);
    }

    /**
     * 添加一个String类型参数
     * @param key
     * @param value
     */
    public void add(String key,String value){
        parameter.put(key,value);
    }

    /**
     * 获取一个指定key的String[]类型参数
     * @param key
     * @return
     */
    public String[] getArray(String key){
        return parameterArray.get(key);
    }

    /**
     * 添加一个指定key的String[]类型参数
     * @param key
     * @return
     */
    public void addArray(String key,String[] value){
        parameterArray.put(key,value);
        parameter.put(key,value!=null&&value.length!=0?value[0]:null);
    }

    /**
     * 获取一个Object类型参数
     * @param key
     * @return
     */
    public Object getObject(String key){
        return parameterObject.get(key);
    }

    /**
     * 添加一个Object类型参数
     * @param key
     * @param value
     */
    public void addObject(String key,Object value){
        parameterObject.put(key,value);
    }

    /**
     * 获取一个Integer类型参数
     * @param key
     * @return
     */
    public Integer getInteger(String key){
        return parameterInteger.get(key);
    }


    /**
     * 从Integer或者String集合中获取参数
     * @param key
     * @return
     */
    public String getIntegerOrString(String key){
        if(hasKeyInteger(key)){
            return String.valueOf(getInteger(key));
        }else{
            return get(key);
        }
    }
    /**
     * 添加一个Integer类型参数
     * @param key
     * @param value
     */
    public void addInteger(String key,Integer value){
        parameterInteger.put(key,value);
    }

    /**
     * 从参数map中获取指定key的值,验证字符串是否存在
     * @param key
     * @return
     */
    public  boolean hasKeyText(String key) {
        String str=get(key);
        if (str != null && !"".equals(str)) {
            return str.trim().length() > 0;
        } else {
            return false;
        }
    }

    /**
     * 从参数map中获取指定key的值,验证数组是否存在
     * @param key
     * @return
     */
    public  boolean hasKeyArray(String key) {
        String[] str=getArray(key);
        return str!=null && str.length!=0 && StringUtils.hasText(str[0]);
    }

    /**
     * 从参数map中获取指定key的值,验证数字是否存在
     * @param key
     * @return
     */
    public  boolean hasKeyInteger(String key) {
        Integer num=getInteger(key);
        return num!=null;
    }

    /**
     * 从参数map中获取指定key的值,验证字符串或者数字是否存在
     * @param key
     * @return
     */
    public  boolean hasKeyTextOrInteger(String key) {
        return hasKeyText(key)||hasKeyInteger(key);
    }

    /**
     * String类型转为Integer类型
     * @param value
     * @return
     */
    public Integer stringToInteger(String value){
        if(value==null){
            return null;
        }
        try {
            Integer res=Integer.valueOf(value);
            return res;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Map toAllMap(){
        Map<String,Object> map=new HashMap<>();
        map.putAll(parameter);
        map.putAll(parameterInteger);
        map.putAll(parameterArray);
        map.putAll(parameterObject);
        return map;
    }


}
