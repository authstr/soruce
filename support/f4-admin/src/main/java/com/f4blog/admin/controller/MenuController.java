package com.f4blog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4Blog.basic.reqres.response.ResponseData;
import com.f4Blog.basic.web.controller.AbstractController;
import com.f4blog.admin.service.inter.MenuService;
import com.f4blog.model.base.BaseMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api("菜单组管理")
@RequestMapping("menu")
@RestController
public class MenuController extends AbstractController {

    @Autowired
    MenuService MenuService;

    @ApiOperation(value="分页查询", notes="查询菜单组",httpMethod="get")
    @RequestMapping("query")
    public ResponseData query(@Param("page") Page page, String name) {
        Page page1= MenuService.query(page, name);
        return ResponseData.success(page1);
    }

    @ApiOperation(value="添加或编辑", notes="",httpMethod="post")
    @RequestMapping("add_or_edit")
    public ResponseData add_or_edit(@Valid @RequestBody BaseMenu model, HttpServletRequest request) {
        RequestPara para= new RequestPara(request);
        MenuService.addOrEdit(model,para);
        return ResponseData.success();
    }

    @ApiOperation(value="删除菜单组(不包括子菜单组)", notes="",httpMethod="post")
    @RequestMapping("delete")
    public ResponseData delete(Integer[] ids) {
        MenuService.delete(ids);
        return ResponseData.success();
    }

    @ApiOperation(value="删除菜单组(包括子菜单组)", notes="",httpMethod="post")
    @RequestMapping("delete_chilren")
    public ResponseData delete_chilren(Integer[] ids) {
        MenuService.delete(ids);
        return ResponseData.success();
    }


    @ApiOperation(value="获取所有", notes="",httpMethod="post")
    @RequestMapping("getAll")
    public ResponseData getAll(HttpServletRequest request) {
        RequestPara para= new RequestPara(request);
        return ResponseData.success(MenuService.getAll(para));
    }

    @RequestMapping("getById")
    public ResponseData getById( Integer id) {
        return ResponseData.success(MenuService.getById(id));
    }





}

