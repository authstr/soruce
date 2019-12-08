package com.f4blog.admin.service.inter;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4blog.model.base.BaseDict;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DictService extends IService<BaseDict> {

    Page query(@Param("page") Page page,Integer type_id, String name);

    @Transactional(rollbackFor=Exception.class)
    void addOrEdit(BaseDict model, RequestPara para);

    BaseDict test(Integer id);

    @Transactional(rollbackFor=Exception.class)
    void delete(Integer[] ids);

    @Transactional(rollbackFor=Exception.class)
    void enableOrDis(Integer[] ids, Integer status);
}
