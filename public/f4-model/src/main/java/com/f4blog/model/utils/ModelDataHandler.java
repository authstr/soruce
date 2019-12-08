package com.f4blog.model.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定义model在保存或者填充时,要进行的数据操作
 * @time 2019年9月30日16:04:27
 * @author authstr
 */
@Component
public class ModelDataHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //设置创建时间
        this.setFieldValByName("gmt_create", new Date(), metaObject);
        //设置更新时间
        this.setFieldValByName("gmt_modified", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //设置更新时间
        this.setFieldValByName("gmt_modified", new Date(), metaObject);
    }
}
