package com.f4blog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4Blog.auth.service.inter.UserService;
import com.f4Blog.basic.web.controller.AbstractController;
import com.f4blog.admin.mapper.inter.DictDao;
import com.f4blog.admin.service.impl.DictTypeServiceImpl;
import com.f4blog.admin.service.inter.DictTypeService;
import com.f4blog.model.base.BaseDict;
import com.f4blog.model.base.BaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class test extends AbstractController {

    @Autowired
    UserService userService;

    @Autowired
    DictTypeServiceImpl dictTypeService;

    @Autowired
    DictDao dictDao;

    @RequestMapping("test")
    public Map test() {
        System.out.println("ddd");
//        Page<BaseUser> a = new Page<>();
//        Page<BaseUser> baseUserIPage = d.selectPageVo(a);
//        Map res = success();
//        res.put("page", baseUserIPage);
//        BaseUser a=userService.getByUsername("aaaa");
//        dictTypeService.test();
        BaseDict a= dictDao.selectById(1);
        Map success=super.success();
        success.put("ss","555");
        return success;
    }
//
//    @RequestMapping("test2")
//    public String d() {
//        BaseUser user = d.selectById(1);
//        System.out.println(user);
//        return "777";
//    }
//
//    @RequestMapping("test3")
//    public String ad() {
//        BaseUser user = d.selectById(1);
//        System.out.println(user);
//        return "l";
//    }
//
//    @RequestMapping("test4")
//    public String aer() {
//        BaseUser user = new BaseUser();
//        user.setUsername("55    ");
//        user.setPassword("989");
//        d.insert(user);
//        System.out.println(user.getId());
//
////        System.out.println(user);
//        return "5";
//    }
}
