package com.f4blog.model.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


/**
 * 基本权限表
 */
@Data
@TableName(value = "base_permission")
public class BasePermission extends BaseModel {

	//权限名称
	@NotEmpty(message = "权限名称不能为空")
	private String name;

	//权限类型
	private Integer type;

	//权限说明
	private  String remark;

}
