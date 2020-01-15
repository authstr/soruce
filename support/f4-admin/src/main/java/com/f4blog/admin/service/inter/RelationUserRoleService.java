package com.f4blog.admin.service.inter;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4blog.model.base.BaseDict;
import com.f4blog.model.base.relation.BaseRelationUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RelationUserRoleService extends IService<BaseRelationUserRole> {
}
