package com.f4blog.admin.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4Blog.basic.exception.Assert;
import com.f4Blog.basic.exception.BaseExceptionEnum;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4Blog.basic.web.service.BaseServiceImpl;
import com.f4blog.admin.mapper.inter.GroupDao;
import com.f4blog.admin.service.inter.GroupService;
import com.f4blog.model.base.BaseGroup;
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
        return groupDao.getAll(para.get("name"),para.get("exclude_id"));
    };

    /**
     * 添加或编辑
     * @param model
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void addOrEdit(BaseGroup model, RequestPara para) {
        Assert.isTrue(super.isUnique(model,"name"),"用户组名称不能重复");

        BaseGroup oldModel=super.getById(model.getId());

        //设置 parent_ids属性值和 level属性值
        setParentIds(model);

        //在进行修改时,更新子用户组
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
     * 设置一个用户组的parent_ids属性和level属性
     * @param model
     */
    public void setParentIds(BaseGroup model){
        Assert.isTrue(model!=null,BaseExceptionEnum.PARA_ERROR);
        BaseGroup oldModel=super.getById(model.getId());
        Integer parent_id=model.getParent_id();

        //如果修改了父用户组,验证设置的父对象是否合法,不能将自身的子对象设置为自己的父对象
        if(model.getParent_id()!=null){
            if(!ModelUtil.isNew(model)){
                Assert.isTrue(oldModel!=null,BaseExceptionEnum.PARA_ERROR);
                String old_parent_ids=oldModel.getParent_ids();
                List<BaseGroup> groups=groupDao.getLikeParentIds(oldModel.getId());
                for(BaseGroup group:groups){
                    Assert.isTrue(!group.getId().equals(model.getParent_id()),"不能将自身的子用户组设置为自己的父用户组");
                }
            }
            //获取父用户组的parent_ids
            BaseGroup parentModel=super.getById(parent_id);
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
     * 在修改后,更新所有子用户组的结构
     * @param oldGroup
     * @param newGroup
     */
    public void updataAllChildParentIdsForEdit(BaseGroup oldGroup, BaseGroup newGroup){
        /*
         例子:
         oldGroup是 A ,  A的父级是P  ,newGroup是 N
         A 有 两个子级 ,是 A1 和 A2
         A2 有个子级是 A21
         * */

        /*
        获取所有的子用户组
        相当于获取了 A1,A2,A21 三个对象
        * */
        List<BaseGroup> groups=groupDao.getLikeParentIds(oldGroup.getId());
        /*
         * 获取所有子级的公共父级元素信息
         * A1,A2,A21三个子级元素都有父级元素 [A],[P],
         * 将A的所有父级加上A本身,就是所有子级的公共父级元素
         * */
        String oldPcodesPrefix = oldGroup.getParent_ids() + "[" + oldGroup.getId() + "],";

        //遍历所有子级对象
        for(BaseGroup group:groups){
            //更新当前子用户组的所有父级元素信息
            /*
            获取当前用户组的非公共得到父级元素信息
            A21除了有公共的[A],[P],父级外,还有一个父级[A2]
            用户组的所有父级信息是按层级顺序排的,截去前面的公共父级,后面的就是非公共父级元素信息
            * */
            String oldPcodesSuffix = group.getParent_ids().substring(oldPcodesPrefix.length());
            /*
            创建当前子用户组新的父级元素信息
            N的所有父级元素 + N本身 +当前用户组非公共父级元素信息 = 新的所有父级元素信息
            * */
            String group_parent_ids = newGroup.getParent_ids() + "[" + newGroup.getId() + "]," + oldPcodesSuffix;

            group.setParent_ids(group_parent_ids);
            //更新层级数,用户组的父级元素数量+1
            int level = StrUtil.count(group_parent_ids, "[");
            group.setLevel(level);
            super.updateById(group);
        }
    }

    /**
     *在删除后,修改子用户组的继承关系和层级
     * @param byDelete 被删除的用户组
     */
    public void updataAllChildParentIdsForDelete(BaseGroup byDelete){
        List<BaseGroup> groups=groupDao.getLikeParentIds(byDelete.getId());
        for(BaseGroup group:groups){
            String new_parent_ids=StrUtil.replace(group.getParent_ids(),"["+byDelete.getId()+"]","");
            group.setParent_ids(new_parent_ids);

            group.setParent_id(byDelete.getParent_id());

            group.setLevel(group.getLevel()-1);
            super.updateById(group);
        }
    }

    /**
     * 在删除后,删除子用户组
     * @param byDelete 被删除的用户组
     */
    public void deleteAllChild(BaseGroup byDelete){
        List<BaseGroup> groups=groupDao.getLikeParentIds(byDelete.getId());
        for(BaseGroup group:groups){
            super.removeById(group);
        }
    }

    /**
     * 删除（不包括子用户组）
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
            updataAllChildParentIdsForDelete(baseGroup);
            Assert.isTrue(super.removeById(id),"第"+(i+1)+"条数据删除失败!本次操作无效");

        }
    }

    /**
     * 删除,包括子用户组
     * @param ids
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void deleteChildren(Integer[] ids){
        Assert.isTrue(ids!=null, BaseExceptionEnum.PARA_ERROR);
        for(int i=0;i<ids.length;i++){
            Integer id=ids[i];
            BaseGroup baseGroup=super.getById(id);
            Assert.isTrue(baseGroup!=null,BaseExceptionEnum.PARA_ERROR);
            deleteAllChild(baseGroup);
            super.removeById(id);
        }
    }



}
