package com.f4blog.model.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@TableName("base_dict")	//基本字典表
public class BaseDict extends BaseModel {

	//字典所属 类 型
	@NotNull
	private Integer type_id;

	//字典 名称
	@Length(max = 20)
	@NotEmpty(message = "字典名称不能空")
	private String name;

	//字典 编码 暂时不做使用
	@Length(max = 64)
	private String code;

	//字典排序
	@NotNull(message = "请填写字典排序")
	@Max(value = 255,message = "字典序号不能超过255")
	private Integer sort;


	//字典上级id
	private Integer parent_id;

	//字典所有上级id
	private String parent_ids;

	//字典描述
	@Length(max = 128)
	private String describe_info;

	//状态
	private Integer status;

}
