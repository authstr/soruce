package com.f4Blog.basic.reqres.response;

import com.f4Blog.basic.exception.BasicException;
import com.f4Blog.basic.exception.ExceptionEnumInterface;
import org.springframework.util.StringUtils;

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

    public static SuccessResponseData success(Object object) {
        return new SuccessResponseData(object);
    }


    public static SuccessResponseData success(String code, String message, Object object) {
        return new SuccessResponseData(code, message, object);
    }

    public static ErrorResponseData error(String message) {
        return new ErrorResponseData(message);
    }

    public static ErrorResponseData error(String code, String message) {
        return new ErrorResponseData(code, message);
    }

    public static ErrorResponseData error(String code, String message, Object object) {
        return new ErrorResponseData(code, message, object);
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
