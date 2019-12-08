package com.f4Blog.basic.exception;


import com.f4Blog.basic.reqres.response.ResponseData;

/**
 * 用于快捷的抛出异常
 * @author authstr
 */
public class Assert {
	 /**
	  * 为false时抛出一个"服务器错误"的异常
	 * @param exp 为false抛出异常
	 * @time 2018年9月17日10:10:33
	 * @author authstr
	 */
	public static void isTrue(boolean exp) {
	        Assert.isTrue(exp,BaseExceptionEnum.SERVER_ERROR.getMessage());
	    }

	/**
	 *  为false时抛出一个指定message的异常
	 * @param exp	为false抛出异常
	 * @param message 该异常要显示的信息
	 * @time 2018年9月17日10:13:05
	 * @author authstr
	 */
	public static void isTrue(boolean exp, String message) {
		Assert.isTrue(exp, message, false);
	}

	/**
	 *  为false时抛出一个指定枚举内信息的异常
	 * @param exp	为false抛出异常
	 * @param msgEnum 该异常要使用的异常信息枚举
	 * @time 2019年10月11日16:34:08
	 * @author authstr
	 */
	public static void isTrue(boolean exp, ExceptionEnumInterface msgEnum) {
		Assert.isTrue(exp, msgEnum.getCode(), msgEnum.getMessage());
	}

	public static void isTrue(boolean exp, String code,String message) {
		Assert.isTrue(exp, code, message,null,false);
	}

	/**
	 * 为false时抛出一个指定message的消息异常或者错误异常
	 * @param exp 为false抛出异常
	 * @param message 该异常要显示的信息
	 * @param isError 该异常是否为错误异常
	 * @time 2018年9月17日10:13:17
	 * @author authstr
	 */
	public static void isTrue(boolean exp, String message, boolean isError) {
		 Assert.isTrue(exp, BaseExceptionEnum.SERVER_ERROR.getCode(), message,null,isError);
	}


	/**
	 * 为false时抛出一个指定信息的消息异常或者错误异常
	 * @param exp 为false抛出异常
	 * @param message 异常的详细说明
	 * @param data	该异常要附带的数据信息
	 * @param isError 该异常是否为错误异常
	 * @time 2018年9月26日10:11:19
	 * @author authstr
	 */
	public static void isTrue(boolean exp, String code, String message,Object data,boolean isError){
		 if (!exp) {
			if (!isError) {
				throw new ServiceException(code,message,data);
			}else{
				throw new ErrorException(code,message,data);
			}
		 }
	}

	/**
	 * 通过ResponseData对象信息抛出一个异常
	 * @param responseData
	 */
	public static void isTrue(ResponseData responseData){
		Assert.isTrue(responseData!=null,BaseExceptionEnum.SERVER_ERROR);
		if(!responseData.getSuccess()){
			throw new ServiceException(responseData.getCode(),responseData.getMessage(),responseData.getData());
		}
	}

	/**
	 * 对象为空时,抛出一个异常
	 * @param o
	 * @time 2019年3月21日20:07:56
	 */
	public static void notNull(Object o){
		if(o==null){
			throw new ErrorException(BaseExceptionEnum.PARA_ERROR.getMessage());
		}
	}


}
