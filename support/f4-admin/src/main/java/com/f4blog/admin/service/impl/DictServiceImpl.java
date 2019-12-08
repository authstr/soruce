package com.f4blog.admin.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4Blog.basic.exception.Assert;
import com.f4Blog.basic.exception.BaseExceptionEnum;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4Blog.basic.web.service.BaseServiceImpl;
import com.f4blog.admin.mapper.inter.DictDao;
import com.f4blog.admin.service.inter.DictService;
import com.f4blog.model.base.BaseDict;
import com.f4blog.model.base.BaseDictType;
import com.f4blog.model.constant.AdminC;
import com.f4blog.model.constant.BaseC;
import com.f4blog.model.utils.ModelUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
public class DictServiceImpl extends BaseServiceImpl<DictDao, BaseDict> implements DictService {

    @Autowired
    DictDao dictDao;

    @Override
    public Page query(@Param("page") Page page,Integer type_id, String name) {
        return dictDao.query(page,type_id, name);
    }

    /**
     * 添加或编辑
     * @param model
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void addOrEdit(BaseDict model, RequestPara para) {
        Assert.isTrue(super.isUnique(model,"name","type_id"),"字典名称不能重复");
        Assert.isTrue(super.isUnique(model,"sort","type_id"),"字典序号不能重复");
        //设置状态为正常
        if(ModelUtil.isNew(model)){
            model.setStatus(BaseC.COMMON_STATUS_NORMAL);
        }
        super.saveOrUpdate(model);
    }

    @Override
    public BaseDict test(Integer id) {
        return dictDao.selectById(id);
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
            BaseDict baseDict=super.getById(id);
            Assert.isTrue(baseDict!=null,BaseExceptionEnum.PARA_ERROR);
            Assert.isTrue(super.removeById(id),"第"+(i+1)+"条数据删除失败!");
        }
    }


    /**
     * 启用或禁用
     * @param ids
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void enableOrDis(Integer[] ids, Integer status){
        Assert.isTrue(ids!=null, BaseExceptionEnum.PARA_ERROR);
        Assert.isTrue(status!=null, BaseExceptionEnum.PARA_ERROR);
        for(int i=0;i<ids.length;i++){
            Integer id=ids[i];
            BaseDict baseDict=super.getById(id);
            baseDict.setStatus(status);
            super.updateById(baseDict);
        }
    }


}
