package com.f4blog.model.base;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@TableName("base_dict_type")	//基本字典类型表
public class BaseDictType extends BaseModel {

	//字典类型 名称
	@NotEmpty(message = "字典类型名称不能为空")
	@Length(max = 20)
	private String name;

	//字典类型 编码
	@NotEmpty(message = "字典类型编码不能为空")
	@Length(max = 64)
	private String code;

	//字典序号
	private Integer sort;

	//字典类型 0普通 1系统
	@NotNull(message = "请选择类型")
	private Integer type;

	//字典系统
	private String system_name;

	//字典描述
	@Length(max = 128)
	private String describe_info;

	//状态
	private Integer status;

}
