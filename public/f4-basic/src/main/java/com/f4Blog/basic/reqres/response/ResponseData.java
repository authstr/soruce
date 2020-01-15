package com.f4Blog.basic.reqres.response;

import com.f4Blog.basic.exception.BasicException;
import com.f4Blog.basic.exception.ExceptionEnumInterface;
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

    public static SuccessResponseData success(String key1,Object value1,String key2,Object value2) {
        SuccessResponseData successResponseData=new SuccessResponseData();
        successResponseData.setData( key1, value1, key2, value2);
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
     */
    public void setData(String key1,Object value1,String key2,Object value2) {
        putData(key1,value1);
        putData(key2,value2);
    }


    /**
     * 将一个键值对保存到返回值的data里.
     * data的类型将变更为Map,如果data里已经有了数据,原有的数据将作为 键值对 'data':原来的数据 保存到Map中
     * @param key
     * @param value
     */
    public void putData(String key,Object value){
        Object old_object=this.getData();
        Map<String,Object> mapData=null;
        //判断当前data储存的是否为Map,如果是,往Map里放入数据
        if(old_object instanceof Map){
            mapData=( Map<String,Object>) old_object;
        }else{
            //如果不是map,创建一个map,将原来数据保存到'data'键
            mapData=new HashMap<>();
            if(old_object!=null){
                mapData.put("data",old_object);
            }
            setData(mapData);
        }
        mapData.put(key,value);
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
