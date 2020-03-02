package com.f4Blog.auth.mapper.inter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.f4blog.model.base.relation.BaseRelationRoleMenu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelationRoleMenuDao extends BaseMapper<BaseRelationRoleMenu> {
}
