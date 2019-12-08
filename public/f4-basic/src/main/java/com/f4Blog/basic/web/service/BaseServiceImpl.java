package com.f4Blog.basic.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f4Blog.basic.exception.Assert;
import com.f4Blog.basic.exception.ErrorException;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;


public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements BaseIService<M,T> {
    /**
     * 判断一个对象里一些属性的值在数据库是否具有唯一性
     * @Param entity 实体对象
     * @param fields 字段名称
     * @return
     * @time 2019年10月14日16:30:30
     * @author authstr
     */
    @Override
    public  boolean isUnique(T entity, String... fields) {
        Assert.isTrue(entity!=null ,"实体类为null",true);
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        for(int i=0;i<fields.length;i++){
            String field=fields[i];
            Object value=null;
            try {
                value=PropertyUtils.getProperty(entity,field);
            } catch (Exception e) {
                throw new ErrorException("无法通过字段名["+fields+"]从实体类["+entity.getClass().getName()+"]获取值");
            }
            if(value==null){
                throw new ErrorException("通过字段名["+fields+"]从实体类["+entity.getClass().getName()+"]获取的值是null");
            }
            wrapper.eq(field,value);
        }
        //判断是否加上id限制
        try {
            Object id=PropertyUtils.getProperty(entity,"id");
            if(id!=null){
                wrapper.ne("id",id);
            }
        } catch (Exception e) {
           throw new ErrorException("无法从实体类["+entity.getClass().getName()+"]获取的id的值");
        }
        int count=super.count(wrapper);
        if(count==0) {
             return true;
        }else{
            return false;
        }
    }
}
