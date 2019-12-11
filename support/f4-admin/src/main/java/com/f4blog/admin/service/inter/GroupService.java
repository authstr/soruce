package com.f4blog.admin.service.inter;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4blog.model.base.BaseDict;
import com.f4blog.model.base.BaseGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface GroupService extends IService<BaseGroup> {

    Page query(@Param("page") Page page, String name);

    List<Map> getAll(RequestPara para);

    @Transactional(rollbackFor=Exception.class)
    void addOrEdit(BaseGroup model, RequestPara para);


    @Transactional(rollbackFor=Exception.class)
    void delete(Integer[] ids);

}
