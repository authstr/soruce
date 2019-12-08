package com.f4blog.model.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


/**
 * 记录登录日志
 */
@Data
@TableName(value = "base_login_log")
public class BaseLoginLog extends BaseModel {

	//要登录到/注销的系统
	private String system_name;

	//操作的时间
	private Date enter_time;

	//请求的来源ip
	private String source_ip;

	//请求的来源类型
	private String source_type;

	//浏览器的类型和版本
	private String browser_info;

	//操作类型 1 登录 2注册 3.注销
	private Integer type;

	//进行操作的用户名称
	private String user_name;

	//进行操作的用户id
	private Integer user_id;

	//操作结果
	private String result;


}
