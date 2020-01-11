package com.f4Blog.basic.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f4Blog.basic.exception.Assert;
import com.f4Blog.basic.exception.ErrorException;
import com.f4Blog.basic.util.ReflectionUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


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

    /**
     * 完整更新一个实体,为null的属性也将更新到数据库
     * 该方法不会更新model继承来的属性,如需,需要修改反射调用的方法
     * @param entity
     * @return
     */
    public Boolean updateIntact(T entity){
        Assert.isTrue(entity!=null ,"实体类为null",true);
        UpdateWrapper<T> wrapper=new UpdateWrapper<>();
        //限制id
        try {
            Object id=PropertyUtils.getProperty(entity,"id");
            Assert.isTrue( id!=null,"无法从实体类["+entity.getClass().getName()+"]获取的id的值,不能执行更新操作",true);
            wrapper.eq("id",id);
        } catch (Exception e) {
            throw new ErrorException("无法从实体类["+entity.getClass().getName()+"]获取的id的值");
        }
        //设置要更新的属性和属性值
        Map<String,Object> fieldAndValue= ReflectionUtils.getAllFieldAndValue(entity,false);
        for(Map.Entry<String,Object> temp:fieldAndValue.entrySet()){
            wrapper.set(temp.getKey(),temp.getValue());
        }
        return  super.update(wrapper);
    }

    /**
     * 将指定id的实体,指定的字段更新为null
     * @param entity
     * @param field
     * @return
     */
    public Boolean updateToNull(T entity,String...field){
        Assert.isTrue(entity!=null ,"实体类为null",true);
        Integer id=null;
        try {
            id=(Integer) PropertyUtils.getProperty(entity,"id");
            Assert.isTrue( id!=null,"无法从实体类["+entity.getClass().getName()+"]获取的id的值,不能执行更新操作",true);
        } catch (Exception e) {
            throw new ErrorException("无法从实体类["+entity.getClass().getName()+"]获取的id的值");
        }
        return updateToNull(id,field);
    }


    /**
     * 将指定id的数据的字段字段更新为null
     * @param id
     * @param field
     * @return
     */
    public Boolean updateToNull(Integer id,String...field){
        Assert.isTrue(id!=null,"未指定要更新数据的id");
        UpdateWrapper<T> wrapper=new UpdateWrapper<>();
        wrapper.eq("id",id);
        //设置要更新的属性和属性值
        for(String temp:field){
            Assert.isTrue(StringUtils.hasText(temp),"需要指定字段进行更新");
            Assert.isTrue(!temp.equals("id"),"不能将id字段设置为null");
            wrapper.set(temp,null);
        }
        return  super.update(wrapper);
    }


}
