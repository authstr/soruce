package com.f4blog.admin.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4Blog.basic.exception.Assert;
import com.f4Blog.basic.exception.BaseExceptionEnum;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4Blog.basic.web.service.BaseServiceImpl;
import com.f4blog.admin.mapper.inter.PermissionDao;
import com.f4blog.admin.service.inter.PermissionService;
import com.f4blog.model.base.BasePermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionDao, BasePermission> implements PermissionService {

    @Autowired
    PermissionDao permissionDao;

    @Override
    public Page query(@Param("page") Page page, String name) {
        return permissionDao.query(page, name);
    }

    @Override
    public List<Map> getAll(RequestPara para){
        return permissionDao.getAll(para.get("name"));
    };

    /**
     * 添加或编辑
     * @param model
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void addOrEdit(BasePermission model, RequestPara para) {
        Assert.isTrue(super.isUnique(model,"name"),"权限名称不能重复");
        super.saveOrUpdate(model);
    }


    /**
     * 删除
     * @param ids
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void delete(Integer[] ids){
        Assert.isTrue(ids!=null, BaseExceptionEnum.PARA_ERROR);
        for(int i=0;i<ids.length;i++){
            Integer id=ids[i];
            BasePermission BasePermission=super.getById(id);
            Assert.isTrue(BasePermission!=null,BaseExceptionEnum.PARA_ERROR);
            Assert.isTrue(super.removeById(id),"第"+(i+1)+"条数据删除失败!本次操作无效");

        }
    }



}
