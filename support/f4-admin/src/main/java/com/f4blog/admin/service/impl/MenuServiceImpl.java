package com.f4blog.admin.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4Blog.basic.exception.Assert;
import com.f4Blog.basic.exception.BaseExceptionEnum;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4Blog.basic.web.service.BaseServiceImpl;
import com.f4blog.admin.mapper.inter.MenuDao;
import com.f4blog.admin.service.inter.MenuService;
import com.f4blog.model.base.BaseMenu;
import com.f4blog.model.constant.BaseC;
import com.f4blog.model.utils.ModelUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuDao, BaseMenu> implements MenuService {

    @Autowired
    MenuDao menuDao;

    @Override
    public Page query(@Param("page") Page page, String name) {
        return menuDao.query(page, name);
    }

    @Override
    public List<Map> getAll(RequestPara para){
        return menuDao.getAll(para.get("name"),para.get("exclude_id"));
    };

    /**
     * 添加或编辑
     * @param model
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void addOrEdit(BaseMenu model, RequestPara para) {
        Assert.isTrue(super.isUnique(model,"code"),"菜单编码不能重复");
        if(model.getStatus()==null){
            model.setStatus(BaseC.COMMON_STATUS_NORMAL);
        }

        BaseMenu oldModel=super.getById(model.getId());


        //设置 parent_ids属性值和 level属性值
        setParentIds(model);

        //在进行修改时,更新子菜单
        if(!ModelUtil.isNew(model)){
            updataAllChildParentIdsForEdit(oldModel,model);
        }

        if(ModelUtil.isNew(model)){
            super.saveOrUpdate(model);
        }else{
            super.updateIntact(model);
        }
    }


    /**
     * 设置一个菜单的parent_ids属性和level属性
     * @param model
     */
    public void setParentIds(BaseMenu model){
        Assert.isTrue(model!=null,BaseExceptionEnum.PARA_ERROR);
        BaseMenu oldModel=super.getById(model.getId());
        Integer parent_id=model.getParent_id();

        //如果修改了父菜单,验证设置的父对象是否合法,不能将自身的子对象设置为自己的父对象
        if(model.getParent_id()!=null){
            if(!ModelUtil.isNew(model)){
                Assert.isTrue(oldModel!=null,BaseExceptionEnum.PARA_ERROR);
                String old_parent_ids=oldModel.getParent_ids();
                List<BaseMenu> Menus= menuDao.getLikeParentIds(oldModel.getId());
                for(BaseMenu Menu:Menus){
                    Assert.isTrue(!Menu.getId().equals(model.getParent_id()),"不能将自身的子菜单设置为自己的父菜单");
                }
            }
            //获取父菜单的parent_ids
            BaseMenu parentModel=super.getById(parent_id);
            Assert.isTrue(parentModel!=null,BaseExceptionEnum.PARA_ERROR);
            model.setParent_ids(parentModel.getParent_ids()+"["+model.getParent_id()+"],");

            //设置层级
            int level=0;
            if( parentModel.getLevel()!=null){
                level = parentModel.getLevel();
            }
            model.setLevel(level+1);
        }else{
            model.setParent_ids("");
            model.setLevel(1);
        }
    }

    /**
     * 在修改后,更新所有子菜单的结构
     * @param oldMenu
     * @param newMenu
     */
    public void updataAllChildParentIdsForEdit(BaseMenu oldMenu, BaseMenu newMenu){
        List<BaseMenu> Menus= menuDao.getLikeParentIds(oldMenu.getId());
        String oldPcodesPrefix = oldMenu.getParent_ids() + "[" + oldMenu.getId() + "],";

        for(BaseMenu Menu:Menus){
            String oldPcodesSuffix = Menu.getParent_ids().substring(oldPcodesPrefix.length());
            String Menu_parent_ids = newMenu.getParent_ids() + "[" + newMenu.getId() + "]," + oldPcodesSuffix;

            Menu.setParent_ids(Menu_parent_ids);
            int level = StrUtil.count(Menu_parent_ids, "[");
            Menu.setLevel(level);
            super.updateById(Menu);
        }
    }

    /**
     *在删除后,修改子菜单的继承关系和层级
     * @param byDelete 被删除的菜单
     */
    public void updataAllChildParentIdsForDelete(BaseMenu byDelete){
        List<BaseMenu> Menus= menuDao.getLikeParentIds(byDelete.getId());
        for(BaseMenu Menu:Menus){
            String new_parent_ids=StrUtil.replace(Menu.getParent_ids(),"["+byDelete.getId()+"]","");
            Menu.setParent_ids(new_parent_ids);

            Menu.setParent_id(byDelete.getParent_id());

            Menu.setLevel(Menu.getLevel()-1);
            super.updateById(Menu);
        }
    }

    /**
     * 在删除后,删除子菜单
     * @param byDelete 被删除的菜单
     */
    public void deleteAllChild(BaseMenu byDelete){
        List<BaseMenu> Menus= menuDao.getLikeParentIds(byDelete.getId());
        for(BaseMenu Menu:Menus){
            super.removeById(Menu);
        }
    }

    /**
     * 删除（不包括子菜单）
     * @param ids
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void delete(Integer[] ids){
        Assert.isTrue(ids!=null, BaseExceptionEnum.PARA_ERROR);
        for(int i=0;i<ids.length;i++){
            Integer id=ids[i];
            BaseMenu baseMenu=super.getById(id);
            Assert.isTrue(baseMenu!=null,BaseExceptionEnum.PARA_ERROR);
            updataAllChildParentIdsForDelete(baseMenu);
            Assert.isTrue(super.removeById(id),"第"+(i+1)+"条数据删除失败!本次操作无效");

        }
    }

    /**
     * 删除,包括子菜单
     * @param ids
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void deleteChildren(Integer[] ids){
        Assert.isTrue(ids!=null, BaseExceptionEnum.PARA_ERROR);
        for(int i=0;i<ids.length;i++){
            Integer id=ids[i];
            BaseMenu baseMenu=super.getById(id);
            Assert.isTrue(baseMenu!=null,BaseExceptionEnum.PARA_ERROR);
            deleteAllChild(baseMenu);
            Assert.isTrue(super.removeById(id),"第"+(i+1)+"条数据删除失败!本次操作无效");
        }
    }

}
