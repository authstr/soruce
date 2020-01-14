package com.f4blog.model.base;

import com.baomidou.mybatisplus.annotation.*;
import com.f4Blog.basic.model.AbstractModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.SqlTimestampTypeHandler;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.DateTypeHandler;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * model 的父类
 * 定义了主键的类型和创建时间 创建人的基本实现
 * @author authstr
 *
 */
public class BaseModel extends AbstractModel {

    @Getter
    @Setter
    @TableField("id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    //创建人
    @Getter
    @Setter
    private  Integer creator_id;

    //创建时间
    @Getter
    @Setter
    @DateTimeFormat(pattern="yyyy-MM-dd mm:hh:ss")
    @JsonFormat(pattern="yyyy-MM-dd mm:hh:ss",timezone="GMT+8")
    @TableField(fill= FieldFill.INSERT)
    private  Date gmt_create;

    //更新时间
    @Getter
    @Setter
    @DateTimeFormat(pattern="yyyy-MM-dd mm:hh:ss")
    @JsonFormat(pattern="yyyy-MM-dd mm:hh:ss",timezone="GMT+8")
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private  Date gmt_modified;

}
