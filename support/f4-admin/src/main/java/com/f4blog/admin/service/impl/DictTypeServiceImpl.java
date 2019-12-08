package com.f4blog.admin.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4Blog.basic.exception.Assert;
import com.f4Blog.basic.exception.BaseExceptionEnum;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4Blog.basic.web.service.BaseServiceImpl;
import com.f4blog.admin.mapper.inter.DictTypeDao;
import com.f4blog.admin.service.inter.DictTypeService;
import com.f4blog.model.base.BaseDictType;
import com.f4blog.model.constant.AdminC;
import com.f4blog.model.constant.BaseC;
import com.f4blog.model.utils.ModelUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class DictTypeServiceImpl extends BaseServiceImpl<DictTypeDao, BaseDictType> implements DictTypeService {

    @Autowired
    DictTypeDao dictTypeDao;

    @Override
    public Page query(@Param("page") Page page, String name, Integer status, Integer type) {
        return dictTypeDao.query(page, name, status, type);
    }

    /**
     * 添加或编辑
     * @param model
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void addOrEdit(BaseDictType model, RequestPara para) {
        Assert.isTrue(super.isUnique(model,"name"),"字典类型名称不能重复");
        Assert.isTrue(super.isUnique(model,"code"),"字典类型编号不能重复");
        //设置状态为正常
        model.setStatus(BaseC.COMMON_STATUS_NORMAL);
        //编码转成大写
        model.setCode(model.getCode().toUpperCase());
        //如果是新添加的且没有系统信息,设置为 未知系统
        if(ModelUtil.isNew(model)&&model.getSystem_name()==null){
            model.setSystem_name(BaseC.SYSTEM_CODE_DEFAULT);
        }
        super.saveOrUpdate(model);
    }

    /**
     * 删除用户
     * @param ids
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void delete(Integer[] ids){
        Assert.isTrue(ids!=null, BaseExceptionEnum.PARA_ERROR);
        for(int i=0;i<ids.length;i++){
            Integer id=ids[i];
            BaseDictType baseDictType=super.getById(id);
            Assert.isTrue(baseDictType!=null,BaseExceptionEnum.PARA_ERROR);
            Assert.isTrue( AdminC.DICT_TYPE_COMMON.equals(baseDictType.getType()),"不能删除系统字典!");
            Assert.isTrue(super.removeById(id),"第"+(i+1)+"条数据删除失败!");
        }
    }



}
