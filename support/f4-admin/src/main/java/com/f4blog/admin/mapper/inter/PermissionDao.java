package com.f4blog.admin.mapper.inter;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4blog.model.base.BasePermission;
import com.f4blog.model.base.BaseRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PermissionDao extends BaseMapper<BasePermission> {
    Page query(@Param("page") Page page, String name);

    List<Map> getAll(String name);
}
