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
import org.springframework.util.StringUtils;

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
        return roleDao.getAll(para.get("name"),para.get("exclude_id"));
    };

    /**
     * 添加或编辑
     * @param model
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void addOrEdit(BaseRole model, RequestPara para) {
        Assert.isTrue(super.isUnique(model,"name"),"角色名称不能重复");

        BaseRole oldModel=super.getById(model.getId());

        //设置 parent_ids属性值和 level属性值
        setParentIds(model);

        //在进行修改时,更新子角色
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
     * 设置一个角色的parent_ids属性和level属性
     * @param model
     */
    public void setParentIds(BaseRole model){
        Assert.isTrue(model!=null,BaseExceptionEnum.PARA_ERROR);
        BaseRole oldModel=super.getById(model.getId());
        Integer parent_id=model.getParent_id();

        //如果修改了父角色,验证设置的父对象是否合法,不能将自身的子对象设置为自己的父对象
        if(model.getParent_id()!=null){
            if(!ModelUtil.isNew(model)){
                Assert.isTrue(oldModel!=null,BaseExceptionEnum.PARA_ERROR);
                String old_parent_ids=oldModel.getParent_ids();
                List<BaseRole> roles=roleDao.getLikeParentIds(oldModel.getId());
                for(BaseRole role:roles){
                    Assert.isTrue(!role.getId().equals(model.getParent_id()),"不能将自身的子角色设置为自己的父角色");
                }
            }
            //获取父角色的parent_ids
            BaseRole parentModel=super.getById(parent_id);
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
     * 在修改后,更新所有子角色的结构
     * @param oldRole
     * @param newRole
     */
    public void updataAllChildParentIdsForEdit(BaseRole oldRole, BaseRole newRole){
        /*
         例子:
         oldRole是 A ,  A的父级是P  ,newRole是 N
         A 有 两个子级 ,是 A1 和 A2
         A2 有个子级是 A21
         * */

        /*
        获取所有的子角色
        相当于获取了 A1,A2,A21 三个对象
        * */
        List<BaseRole> roles=roleDao.getLikeParentIds(oldRole.getId());
        /*
         * 获取所有子级的公共父级元素信息
         * A1,A2,A21三个子级元素都有父级元素 [A],[P],
         * 将A的所有父级加上A本身,就是所有子级的公共父级元素
         * */
        String oldPcodesPrefix = oldRole.getParent_ids() + "[" + oldRole.getId() + "],";

        //遍历所有子级对象
        for(BaseRole role:roles){
            //更新当前子角色的所有父级元素信息
            /*
            获取当前角色的非公共得到父级元素信息
            A21除了有公共的[A],[P],父级外,还有一个父级[A2]
            角色的所有父级信息是按层级顺序排的,截去前面的公共父级,后面的就是非公共父级元素信息
            * */
            String oldPcodesSuffix = role.getParent_ids().substring(oldPcodesPrefix.length());
            /*
            创建当前子角色新的父级元素信息
            N的所有父级元素 + N本身 +当前角色非公共父级元素信息 = 新的所有父级元素信息
            * */
            String role_parent_ids = newRole.getParent_ids() + "[" + newRole.getId() + "]," + oldPcodesSuffix;

            role.setParent_ids(role_parent_ids);
            //更新层级数,角色的父级元素数量+1
            int level = StrUtil.count(role_parent_ids, "[");
            role.setLevel(level);
            super.updateById(role);
        }
    }

    /**
     *在删除后,修改子角色的继承关系和层级
     * @param byDelete 被删除的角色
     */
    public void updataAllChildParentIdsForDelete(BaseRole byDelete){
        List<BaseRole> roles=roleDao.getLikeParentIds(byDelete.getId());
        for(BaseRole role:roles){
            String new_parent_ids=StrUtil.replace(role.getParent_ids(),"["+byDelete.getId()+"]","");
            role.setParent_ids(new_parent_ids);

            role.setParent_id(byDelete.getParent_id());

            role.setLevel(role.getLevel()-1);
            super.updateById(role);
        }
    }

    /**
     * 在删除后,删除子角色
     * @param byDelete 被删除的角色
     */
    public void deleteAllChild(BaseRole byDelete){
        List<BaseRole> roles=roleDao.getLikeParentIds(byDelete.getId());
        for(BaseRole role:roles){
            super.removeById(role);
        }
    }

    /**
     * 删除（不包括子角色）
     * @param ids
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void delete(Integer[] ids){
        Assert.isTrue(ids!=null, BaseExceptionEnum.PARA_ERROR);
        for(int i=0;i<ids.length;i++){
            Integer id=ids[i];
            BaseRole baseRole=super.getById(id);
            Assert.isTrue(baseRole!=null,BaseExceptionEnum.PARA_ERROR);
            updataAllChildParentIdsForDelete(baseRole);
            Assert.isTrue(super.removeById(id),"第"+(i+1)+"条数据删除失败!本次操作无效");

        }
    }

    /**
     * 删除,包括子角色
     * @param ids
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void deleteChildren(Integer[] ids){
        Assert.isTrue(ids!=null, BaseExceptionEnum.PARA_ERROR);
        for(int i=0;i<ids.length;i++){
            Integer id=ids[i];
            BaseRole baseRole=super.getById(id);
            Assert.isTrue(baseRole!=null,BaseExceptionEnum.PARA_ERROR);
            deleteAllChild(baseRole);
            super.removeById(id);
        }
    }

    @Override
    public List<String> getRoleIdByUserId(String userId){
        Assert.isTrue(StringUtils.hasText(userId),BaseExceptionEnum.PARA_ERROR);
        return roleDao.getRoleIdByUserId(userId);
    }

}
