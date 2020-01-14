package com.f4blog.admin.mapper.inter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.f4blog.model.base.relation.BaseRelationUserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelationUserRoleDao extends BaseMapper<BaseRelationUserRole> {
}
