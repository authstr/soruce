package com.f4blog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4Blog.basic.reqres.response.ResponseData;
import com.f4Blog.basic.web.controller.AbstractController;
import com.f4blog.admin.service.inter.UserService;
import com.f4blog.model.base.BaseUser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequestMapping("user")
@RestController
public class UserController extends AbstractController {

    @Autowired
    UserService userService;

    @ApiOperation(value="分页查询", notes="根据User对象创建用户")
    @RequestMapping("query")
    public ResponseData query(@Param("page") Page page, String role, String gmt_create_start, String gmt_create_end, String username) {
        Page page1=userService.query( page, role,  gmt_create_start, gmt_create_end,  username);
        return ResponseData.success(page1);
    }

    @ApiOperation(value="获取用户列表", notes="")
    @RequestMapping(value = "add_or_edit",method = RequestMethod.POST )
    public ResponseData add_or_edit(@Valid @RequestBody  BaseUser user) {
        return ResponseData.success(userService.addOrEdit(user));
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public ResponseData delete(Integer[] ids) {
        userService.delete(ids);
        return ResponseData.success();
    }

    @RequestMapping(value = "getById",method = RequestMethod.POST)
    public ResponseData getById( String id) {
        return ResponseData.success( userService.getById(id));
    }



}

