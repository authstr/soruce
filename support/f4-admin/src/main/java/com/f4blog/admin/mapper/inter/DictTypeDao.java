package com.f4blog.admin.mapper.inter;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4blog.model.base.BaseDict;
import com.f4blog.model.base.BaseDictType;
import org.apache.ibatis.annotations.Param;

public interface DictTypeDao extends BaseMapper<BaseDictType> {
    Page query(@Param("page") Page page,@Param("name") String name,
               @Param("status") Integer status, @Param("type")Integer type);
}
