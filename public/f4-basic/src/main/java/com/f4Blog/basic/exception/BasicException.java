package com.f4Blog.basic.exception;


/**
 * 所有自定义异常的父类
 * @time 2018年9月26日10:53:05
 * @author authstr
 */
public class BasicException extends RuntimeException {

	public final static  String DEFAULT_CODE=BaseExceptionEnum.SERVER_ERROR.getCode();
	String code;
	Object data;
	public BasicException() {
		super();
	}

	public BasicException(String message) {
		super(message);
		code=DEFAULT_CODE;
	}


	public BasicException(String code, String message){
		this(message);
		this.code=code;
	}
	public BasicException(String code, String message, Object data){
		this(message);
		this.code=code;
		this.data=data;
	}

	/**
	 * 通过消息枚举创建异常
	 * @param msgEnum
	 */
	public BasicException(ExceptionEnumInterface msgEnum){
		this(msgEnum.getCode(),msgEnum.getMessage());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
