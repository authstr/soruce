package com.f4Blog.basic.exception;

/**
 * 消息型异常,用来在接口返回值显示消息
 * @time 2018年9月26日10:52:51
 * @author authstr
 *
 */
public class ServiceException extends BasicException {
	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
		code=DEFAULT_CODE;
	}

	public ServiceException(ExceptionEnumInterface msgEnum) {
		super(msgEnum.getCode(),msgEnum.getMessage());
	}
	
	public ServiceException(String code, String message){
		this(message);
		this.code=code;
	}
	public ServiceException(String code, String message, Object data){
		this(message);
		this.code=code;
		this.data=data;
	}
}
