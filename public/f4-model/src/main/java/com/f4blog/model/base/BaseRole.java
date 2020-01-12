package com.f4blog.model.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


/**
 * 基本角色表
 */
@Data
@TableName(value = "base_role")
public class BaseRole extends BaseModel {

	//用户组名称
	@NotEmpty(message = "角色名称不能为空")
	private String name;

	//角色说明
	private  String remark;

}
