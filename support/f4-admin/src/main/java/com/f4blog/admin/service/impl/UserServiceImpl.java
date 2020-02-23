package com.f4blog.admin.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f4Blog.basic.exception.Assert;
import com.f4Blog.basic.exception.BaseExceptionEnum;
import com.f4Blog.basic.exception.ServiceException;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4Blog.basic.shiro.ShiroKit;
import com.f4Blog.basic.util.Md5Salt;
import com.f4Blog.basic.web.service.BaseServiceImpl;
import com.f4blog.admin.mapper.inter.RelationUserRoleDao;
import com.f4blog.admin.mapper.inter.UserDao;
import com.f4blog.admin.service.inter.RelationUserRoleService;
import com.f4blog.admin.service.inter.UserService;
import com.f4blog.admin.util.MsgEnum;
import com.f4blog.model.base.BaseUser;
import com.f4blog.model.base.relation.BaseRelationUserRole;
import com.f4blog.model.constant.BaseC;
import com.f4blog.model.utils.ModelUtil;
import com.f4blog.model.constant.AdminC;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, BaseUser> implements UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    RelationUserRoleService relationUserRoleService;

    @Override
    public Page query(@Param("page")Page page, String role, String gmt_create_start, String gmt_create_end, String username) {
        return userDao.query(page,role,gmt_create_start,gmt_create_end,username);
    }

    /**
     * 添加或编辑用户
     * 可选参数:
     *      role_ids  角色id数组
     * @param model
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer addOrEdit(BaseUser model, RequestPara  para){
        BaseUser user_temp=getByUsername(model.getUsername());
        //如果user_temp不为空,但id一样,说明是进行不改名字的编辑,不视为用户名存在
        Assert.isTrue(user_temp==null||user_temp.getId().equals(model.getId()),"该用户名已存在");
        Integer id=null;
        if(ModelUtil.isNew(model)){
            super.save(model);
        }
        id= model.getId();
        Assert.isTrue(id!=null,"添加用户失败");
        //密码md5加密后,用数据自身id作为盐,再次加密
        String md5Password= null;
        try {
            md5Password = Md5Salt.sec(String.valueOf(id),model.getPassword());
        } catch (Exception e) {
            throw new ServiceException(MsgEnum.ENCRYPT_ERROR);
        }
        model.setPassword(md5Password);
        super.updateById(model);

        //设置用户的角色
        String[] role_ids=para.getArray("role_ids");

        if(role_ids!=null){
            Integer[] role_ids_int= (Integer[]) ConvertUtils.convert(role_ids, Integer.class);
            saveRoleInfo(id,role_ids_int);
        }

        return model.getId();
    }

    @Transactional(rollbackFor=Exception.class)
//    @Override
    public void addUser(BaseUser model,Integer user_type,RequestPara  para){
        BaseUser user_temp=getByUsername(model.getUsername());
        Assert.isTrue(user_temp==null,MsgEnum.USER_EXIST);
        // 获取盐值和加密后的密码
        String salt = ShiroKit.getRandomSalt(5);
        String password = ShiroKit.md5(model.getPassword(), salt);

        //保存
        model.setSalt(salt);
        model.setPassword(password);
        model.setUser_type(user_type);
        model.setStatus(BaseC.COMMON_STATUS_NORMAL);
        super.save(model);

        //设置用户的角色
        String[] role_ids=para.getArray("role_ids");
        if(role_ids!=null){
            Integer[] role_ids_int= (Integer[]) ConvertUtils.convert(role_ids, Integer.class);
            saveRoleInfo(model.getId(),role_ids_int);
        }
    }

    @Override
    public void register(BaseUser model,RequestPara  para){
        addUser(model,AdminC.USER_TYPE_COMMON,para);
    }


    /**
     * 为一个用户保存角色信息
     * @param userId
     * @param roleIds
     * @return
     */
    @Override
    @Transactional
    public Boolean saveRoleInfo(Integer userId, Integer[] roleIds){
        Assert.isTrue( userId!=null,"userId不能为空",true);
        Assert.isTrue( roleIds!=null,"roleIds不能为空",true);
            //删除当前用户的角色关联信息
        Map<String,Object> delete_where=new HashMap();
        delete_where.put("user_id",userId);
        relationUserRoleService.removeByMap(delete_where);

        //创建角色关联信息并保存
        List<BaseRelationUserRole> temp=new ArrayList();
        for(int i=0;i<roleIds.length;i++){
            BaseRelationUserRole relation=new BaseRelationUserRole();
            relation.setUser_id(userId);
            relation.setRole_id(roleIds[i]);
            temp.add(relation);
        }
        return relationUserRoleService.saveBatch(temp);
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
            Assert.isTrue( !id.equals(AdminC.ADMIN_USER_ID),"不能删除超级管理员!");
            Assert.isTrue(super.removeById(id),"第"+(i+1)+"条数据删除失败!");
        }
    }


    @Override
    public BaseUser getByUsername(String username){
        return userDao.getByUsername(username);
    }

}
