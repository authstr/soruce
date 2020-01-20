package com.f4blog.model.base.relation;

import com.baomidou.mybatisplus.annotation.TableName;
import com.f4blog.model.base.BaseModel;
import lombok.Data;


/**
 * 角色 与 菜单 关联表
 */
@Data
@TableName(value = "base_relation_role_menu")
public class BaseRelationRoleMenu extends BaseModel {

	//角色id
	private Integer role_id;

	//菜单id
	private Integer menu_id;

}
