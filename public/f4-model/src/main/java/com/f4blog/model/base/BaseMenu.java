package com.f4blog.model.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * 基本菜单表
 */
@Data
@TableName(value = "base_menu")
public class BaseMenu extends BaseModel {

	//菜单名称
	@NotEmpty(message = "菜单名称不能为空")
	private String name;

	//排序号
	@NotNull(message = "菜单的排序号不能为空")
	private Integer orderno;

	//图标url
	private String icon_uri;

	//菜单的参数
	private  String para;

	//菜单的类型 1 系统 2菜单 3 按钮
	private  Integer type;

	//菜单的url
	private  String url;

	//父菜单id
	private  Integer parent_id;

	//该菜单所有的父菜单id集合,数据格式:[0],[2],[152],
	private String parent_ids;

	//该菜单的层级,一般与父菜单的数量一致
	private Integer level;

	//菜单的状态 -1禁用 0有效
	private  Integer status;

	//菜单说明
	private  String remark;

}
