package com.f4blog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4Blog.basic.reqres.response.ResponseData;
import com.f4Blog.basic.web.controller.AbstractController;
import com.f4blog.admin.service.inter.PermissionService;
import com.f4blog.model.base.BasePermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api("权限管理")
@RequestMapping("permission")
@RestController
public class PermissionController extends AbstractController {

    @Autowired
    PermissionService permissionService;

    @ApiOperation(value="分页查询", notes="查询权限",httpMethod="get")
    @RequestMapping("query")
    public ResponseData query(@Param("page") Page page, String name) {
        Page page1= permissionService.query(page, name);
        return ResponseData.success(page1);
    }

    @ApiOperation(value="添加或编辑", notes="",httpMethod="post")
    @RequestMapping("add_or_edit")
    public ResponseData add_or_edit(@Valid @RequestBody BasePermission model, HttpServletRequest request) {
        RequestPara para= new RequestPara(request);
        permissionService.addOrEdit(model,para);
        return ResponseData.success();
    }

    @ApiOperation(value="删除权限", notes="",httpMethod="post")
    @RequestMapping("delete")
    public ResponseData delete(Integer[] ids) {
        permissionService.delete(ids);
        return ResponseData.success();
    }



    @ApiOperation(value="获取所有", notes="",httpMethod="post")
    @RequestMapping("getAll")
    public ResponseData getAll(HttpServletRequest request) {
        RequestPara para= new RequestPara(request);
        return ResponseData.success(permissionService.getAll(para));
    }

    @RequestMapping("getById")
    public ResponseData getById( Integer id) {
        return ResponseData.success(permissionService.getById(id));
    }


}

