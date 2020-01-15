package com.f4blog.model.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.logging.Level;


/**
 * 基本角色表
 */
@Data
@TableName(value = "base_role")
public class BaseRole extends BaseModel {

	//角色别名
	@NotEmpty(message = "角色别名不能为空")
	private String code;

	//角色名称
	@NotEmpty(message = "角色名称不能为空")
	private String name;

	//父角色id
	private  Integer parent_id;

	//该角色所有的父角色id集合,数据格式:[0],[2],[152],
	private String parent_ids;

	//该角色的层级,一般与父角色的数量一致
	private Integer level;

	//角色说明
	private  String remark;

}
