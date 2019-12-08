package com.f4blog.admin.mapper.inter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4blog.model.base.BaseUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao extends BaseMapper<BaseUser> {
    Page query(@Param("page") Page<BaseUser> page, @Param("role")String role,
               @Param("gmt_create_start") String gmt_create_start,@Param("gmt_create_end") String gmt_create_end,
               @Param("username")String username);

    BaseUser getByUsername(@Param("username") String username);
}
