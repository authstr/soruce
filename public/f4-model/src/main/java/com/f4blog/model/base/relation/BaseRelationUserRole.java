package com.f4blog.model.base.relation;

import com.baomidou.mybatisplus.annotation.TableName;
import com.f4blog.model.base.BaseModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * 用户 与 角色 关联表
 */
@Data
@TableName(value = "base_relation_user_role")
public class BaseRelationUserRole extends BaseModel {

	//用户id
	private Integer user_id;

	//角色id
	private Integer role_id;

}
