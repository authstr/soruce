package com.f4blog.admin.mapper.inter;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4blog.model.base.BaseDict;
import com.f4blog.model.base.BaseGroup;
import org.apache.ibatis.annotations.Param;

public interface GroupDao extends BaseMapper<BaseGroup> {
    Page query(@Param("page") Page page, String name);
}
