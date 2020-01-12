package com.f4blog.admin.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4Blog.basic.exception.Assert;
import com.f4Blog.basic.exception.BaseExceptionEnum;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4Blog.basic.web.service.BaseServiceImpl;
import com.f4blog.admin.mapper.inter.RoleDao;
import com.f4blog.admin.service.inter.RoleService;
import com.f4blog.model.base.BaseRole;
import com.f4blog.model.utils.ModelUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleDao, BaseRole> implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Override
    public Page query(@Param("page") Page page, String name) {
        return roleDao.query(page, name);
    }

    @Override
    public List<Map> getAll(RequestPara para){
        return roleDao.getAll(para.get("name"));
    };

    /**
     * 添加或编辑
     * @param model
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void addOrEdit(BaseRole model, RequestPara para) {
        Assert.isTrue(super.isUnique(model,"name"),"角色名称不能重复");
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
            BaseRole BaseRole=super.getById(id);
            Assert.isTrue(BaseRole!=null,BaseExceptionEnum.PARA_ERROR);
            Assert.isTrue(super.removeById(id),"第"+(i+1)+"条数据删除失败!本次操作无效");

        }
    }



}
