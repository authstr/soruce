package com.f4blog.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.f4blog.model.base.BaseUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<BaseUser> {
}
