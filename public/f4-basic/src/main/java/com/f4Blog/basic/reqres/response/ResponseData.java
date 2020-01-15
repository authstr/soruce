package com.f4Blog.basic.reqres.response;

import com.f4Blog.basic.exception.BasicException;
import com.f4Blog.basic.exception.ExceptionEnumInterface;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 对请求返回的数据进行封装  父类
 * @author authstr
 * @time 2019年10月11日14:10:28
 */
public class ResponseData {
    private Boolean success;
    private String code;
    private String message;
    private Object data;

    public ResponseData(){}

    public ResponseData(Boolean success,String code,String message,Object data){
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }


    /**
     * 将枚举的信息添加到对象
     * @param msgenum
     */
    public void addEnumInfo( ExceptionEnumInterface msgenum ){
        setCode(msgenum.getCode());
        setMessage(msgenum.getMessage());
    }

    /**
     * 将异常的信息添加到对象
     * @param e
     */
    public void addExceptionInfo( BasicException e ){
        setSuccess(false);
        if(StringUtils.hasText(e.getCode())){
            setCode(e.getCode());
        }
        if(StringUtils.hasText(e.getMessage())){
            setMessage(e.getMessage());
        }
        if(e.getData()!=null){
            setData(e.getData());
        }
        //获取异常的行号信息 进行储存
        //该功能后续再补充
    }


    public static SuccessResponseData success() {
        return new SuccessResponseData();
    }

    public static SuccessResponseData success(Object data) {
        return new SuccessResponseData(data);
    }

    /**
     * 创建一个默认的成功返回对象.之后以键值对的方式,保存两个对象到Date
     * @param key1 对象1的key
     * @param value1 对象1
     * @param key2 对象2的key
     * @param value2 对象2
     */
    public static SuccessResponseData success(String key1,Object value1,String key2,Object value2) {
        SuccessResponseData successResponseData=new SuccessResponseData();
        successResponseData.setData( key1, value1, key2, value2);
        return successResponseData;
    }

    /**
     * 创建一个默认的成功返回对象.之后以父子关系的方式,保存两个对象到Date.父对象将转换为map作为data,子对象将作为map的一个键值对
     * @param parentData 父对象
     * @param childName 子对象的key
     * @param childData 子对象
     */
    public static SuccessResponseData success(Object parentData,String childName,Object childData) {
        SuccessResponseData successResponseData=new SuccessResponseData();
        successResponseData.setParentChildData( parentData, childName, childData);
        return successResponseData;
    }


    public static SuccessResponseData success(String code, String message, Object data) {
        return new SuccessResponseData(code, message, data);
    }

    public static ErrorResponseData error(String message) {
        return new ErrorResponseData(message);
    }

    public static ErrorResponseData error(String code, String message) {
        return new ErrorResponseData(code, message);
    }

    public static ErrorResponseData error(String code, String message, Object data) {
        return new ErrorResponseData(code, message, data);
    }


    /**
     * 用于以键值对的方式,保存两个对象到Date
     * @param key1 对象1的key
     * @param value1 对象1
     * @param key2 对象2的key
     * @param value2 对象2
     */
    public void setData(String key1,Object value1,String key2,Object value2) {
        putData(key1,value1);
        putData(key2,value2);
    }


    /**
     * 用于以父子关系的方式,保存两个对象到Date.父对象将转换为map作为data,子对象将作为map的一个键值对
     * @param parentData 父对象
     * @param childName 子对象的key
     * @param childData 子对象
     */
    public void setParentChildData(Object parentData,String childName,Object childData) {
        putData(parentData);
        putData(childName,childData);
    }

    /**
     * 将字段data设置为Map对象,原来的对象会转换为Map进行保存,无法转换则保存到'data'键
     */
    public Map<String,Object> dataSetMap(){
        Object old_object=this.getData();
        //判断当前data储存的是否为Map
        if(!(old_object instanceof Map)){
            Map<String,Object> mapData=null;
            //如果不是map,创建一个map, 原来的对象会转换为Map进行保存,无法转换保存到'data'键
            mapData=new HashMap<>();
            if(old_object!=null){
                try {
                    Map<String,Object> tmep=PropertyUtils.describe(old_object);
                    //移除无关的class键
                    tmep.remove("class");
                    mapData.putAll(tmep);
                }catch (Exception e){
                    e.printStackTrace();
                    mapData.put("data",old_object);
                }
            }
            setData(mapData);
        }
        return (Map<String,Object> )getData();
    }


    /**
     * 将一个键值对保存到 data的 map 里
     * @param key
     * @param value
     */
    public void putData(String key,Object value){
        Map<String,Object> mapData=dataSetMap();
        mapData.put(key,value);
    }

    /**
     * 将一个对象转换为map,合并到 data的map 里
     * @param entity
     */
    public void putData(Object entity){
        Map<String,Object> mapData=dataSetMap();
        if(entity!=null){
            try {
                Map<String,Object> tmep= PropertyUtils.describe(entity);
                //移除无关的class键
                tmep.remove("class");
                mapData.putAll(tmep);
            }catch (Exception e){
                e.printStackTrace();
                mapData.put(entity.getClass().getName(),entity);
            }
        }
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
