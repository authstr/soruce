package com.f4blog.model.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.logging.Level;


/**
 * 基本用户组表
 */
@Data
@TableName(value = "base_group")
public class BaseGroup extends BaseModel {

	//用户组名称
	@NotEmpty(message = "用户组名称不能为空")
	private String name;

	//父用户组id
	private  Integer parent_id;

	//该用户组所有的父用户组id集合,数据格式:[0],[2],[152],
	private String parent_ids;

	//该用户组的层级,一般与父用户组的数量一致
	private Integer level;

	//用户组说明
	private  String remark;

}
