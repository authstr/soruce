package com.f4blog.admin.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4Blog.basic.exception.Assert;
import com.f4Blog.basic.exception.BaseExceptionEnum;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4Blog.basic.web.service.BaseServiceImpl;
import com.f4blog.admin.mapper.inter.DictDao;
import com.f4blog.admin.mapper.inter.GroupDao;
import com.f4blog.admin.service.inter.DictService;
import com.f4blog.admin.service.inter.GroupService;
import com.f4blog.model.base.BaseDict;
import com.f4blog.model.base.BaseGroup;
import com.f4blog.model.constant.BaseC;
import com.f4blog.model.utils.ModelUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class GroupServiceImpl extends BaseServiceImpl<GroupDao, BaseGroup> implements GroupService {

    @Autowired
    GroupDao groupDao;

    @Override
    public Page query(@Param("page") Page page, String name) {
        return groupDao.query(page, name);
    }

    @Override
    public List<Map> getAll(RequestPara para){
        return groupDao.getAll(para.get("name"));
    };

    /**
     * 添加或编辑
     * @param model
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void addOrEdit(BaseGroup model, RequestPara para) {
        Assert.isTrue(super.isUnique(model,"name"),"用户组名称不能重复");
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
            BaseGroup baseGroup=super.getById(id);
            Assert.isTrue(baseGroup!=null,BaseExceptionEnum.PARA_ERROR);
            Assert.isTrue(super.removeById(id),"第"+(i+1)+"条数据删除失败!");
        }
    }



}
