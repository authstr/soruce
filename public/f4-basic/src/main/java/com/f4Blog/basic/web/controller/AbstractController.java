package com.f4Blog.basic.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.f4Blog.basic.exception.BaseExceptionEnum;
import com.f4Blog.basic.exception.ErrorException;
import com.f4Blog.basic.exception.ExceptionEnumInterface;
import com.f4Blog.basic.exception.ServiceException;
import com.f4Blog.basic.reqres.response.ErrorResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.UnexpectedTypeException;


/**
 * 控制层的超类,
 * 主要对控制层的异常进行捕获,通过接口返回或者进行其他处理
 * @author authstr
 * 2019年10月7日18:16:41
 */
@Component
public class AbstractController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 捕获其他异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value={Exception.class})
    @ResponseBody
	public ErrorResponseData exceptionHandler(Exception ex) {
        ErrorResponseData res=new ErrorResponseData();
        //不确定的异常,在返回值显示未知异常,具体错误消息进行记录和打印
		res.addEnumInfo(BaseExceptionEnum.UNKNOWN_ERROR);
		ex.printStackTrace();
		this.log.error( "系统出现未知异常 :" + ex.getMessage());
        return res;
    }




	/**
	 * 捕获消息型的异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value={ServiceException.class})
	@ResponseBody
	public ErrorResponseData exceptionHandler(ServiceException ex) {
		ErrorResponseData res=new ErrorResponseData();
		res.addExceptionInfo(ex);
		return res;
	}

	/**
	 * 捕获出错型的异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value={ErrorException.class})
	@ResponseBody
	public ErrorResponseData exceptionHandler(ErrorException ex) {
		ErrorResponseData res=new ErrorResponseData();
		//出现错误型异常,在返回值显示服务器异常,具体错误消息进行记录和打印
		ex.printStackTrace();
		log.error("系统执行出现异常:" + ex.getMessage());
		return res;
	}

	/**
	 * 捕获数据绑定异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value={BindException.class,MethodArgumentNotValidException.class})
	@ResponseBody
	public ErrorResponseData bindExceptionHandler(Exception ex) {
		ErrorResponseData res=new ErrorResponseData();
		//出现参数不正确的异常,在返回值显示提示信息,具体错误消息进行记录和打印
		//设置为参数错误
		res.addEnumInfo(BaseExceptionEnum.PARA_ERROR);
		List<FieldError> fieldErrors=null;
		if(ex instanceof BindException){
			fieldErrors=((BindException)ex).getBindingResult().getFieldErrors();
		}else if(ex instanceof  MethodArgumentNotValidException){
			fieldErrors=((MethodArgumentNotValidException)ex).getBindingResult().getFieldErrors();
		}else{
			return res;
		}
		List<String> allError=new ArrayList<>();
		//将第一个错误信息作为响应的错误信息
		res.setMessage(fieldErrors.get(0).getDefaultMessage());
		//记录其他错误信息
		for (FieldError error:fieldErrors){
			allError.add(error.getDefaultMessage());
		}
		res.setData(allError);
		return res;
	}



	/**
	 * 获取[成功]的信息map
	 * @return
	 */
	public Map success() {
		return createMsg(BaseExceptionEnum.SUCCESS);
	}

	/**
	 * 通过一个消息的枚举,创建用于响应的map对象
	 * @param msg
	 * @return
	 */
	public static Map createMsg(ExceptionEnumInterface msg) {
		Map map = new HashMap();
		map.put(ControllerConstant.CODE,msg.getCode());
		map.put(ControllerConstant.MSG,msg.getMessage());
		return map;
	}

}
