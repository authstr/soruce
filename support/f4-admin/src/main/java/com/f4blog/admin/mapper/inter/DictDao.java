package com.f4blog.admin.mapper.inter;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4blog.model.base.BaseDict;
import com.f4blog.model.base.BaseUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DictDao  extends BaseMapper<BaseDict> {
    Page query(@Param("page") Page page,Integer type_id, String name);
}
