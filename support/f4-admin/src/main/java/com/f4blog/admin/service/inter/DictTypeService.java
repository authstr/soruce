package com.f4blog.admin.service.inter;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4blog.model.base.BaseDictType;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DictTypeService extends IService<BaseDictType> {

    Page query(@Param("page") Page page, String name, Integer status, Integer type);

    @Transactional(rollbackFor=Exception.class)
    void addOrEdit(BaseDictType model, RequestPara para);

    @Transactional(rollbackFor=Exception.class)
    void delete(Integer[] ids);
}
