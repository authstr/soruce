package com.f4blog.admin.service.inter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4blog.model.base.BaseUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Map;

public interface UserService extends IService<BaseUser> {

    Page query(@Param("page") Page page,
                                    @Param("role") String role,
                                    @Param("gmt_create_start") String gmt_create_start,
                                    @Param("gmt_create_end") String gmt_create_end ,
                                    @Param("username") String username);

    @Transactional(rollbackFor=Exception.class)
    Integer addOrEdit(BaseUser model, RequestPara para);

    void register(BaseUser model, RequestPara para);

    Boolean saveRoleInfo(Integer userId, Integer[] roleIds);

    @Transactional(rollbackFor=Exception.class)
    void delete(Integer[] ids);

    BaseUser getByUsername(String username);
}
