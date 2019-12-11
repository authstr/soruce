package com.f4blog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4Blog.basic.reqres.response.ResponseData;
import com.f4Blog.basic.web.controller.AbstractController;
import com.f4blog.admin.service.inter.GroupService;
import com.f4blog.model.base.BaseGroup;
import com.f4blog.model.constant.BaseC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api("用户组管理")
@RequestMapping("group")
@RestController
public class GroupController extends AbstractController {

    @Autowired
    GroupService groupService;

    @ApiOperation(value="分页查询", notes="查询用户组",httpMethod="get")
    @RequestMapping("query")
    public ResponseData query(@Param("page") Page page, String name) {
        Page page1= groupService.query(page, name);
        return ResponseData.success(page1);
    }

    @ApiOperation(value="添加或编辑", notes="",httpMethod="post")
    @RequestMapping("add_or_edit")
    public ResponseData add_or_edit(@Valid @RequestBody BaseGroup model, HttpServletRequest request) {
        RequestPara para= new RequestPara(request);
        groupService.addOrEdit(model,para);
        return ResponseData.success();
    }

    @RequestMapping("delete")
    public ResponseData delete(Integer[] ids) {
        groupService.delete(ids);
        return ResponseData.success();
    }

    @ApiOperation(value="获取所有", notes="",httpMethod="post")
    @RequestMapping("getAll")
    public ResponseData getAll(HttpServletRequest request) {
        RequestPara para= new RequestPara(request);
        return ResponseData.success(groupService.getAll(para));
    }



    @RequestMapping("getById")
    public ResponseData getById( Integer id) {
        BaseGroup a= groupService.getById(id);
        return ResponseData.success(groupService.getById(id));
    }





}

