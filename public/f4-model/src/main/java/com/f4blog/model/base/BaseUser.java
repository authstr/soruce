package com.f4blog.model.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


@Data
@TableName(value = "base_user")
public class BaseUser  extends BaseModel {

	//用户名
	@NotEmpty(message = "用户名不能为空")
	@Length(min=6,max = 16,message = "用户名需为6-16字符")
	private String username;

	//密码
	@NotEmpty(message = "密码不能为空")
	private String password;

	//	性别
	private String sex_id;

	//邮箱
	private String email;

	//手机号
	private String phone;

	//备注
	private String remark;

	//用户类型
	private Integer user_type;

	//状态
	private Integer status_id;
}
