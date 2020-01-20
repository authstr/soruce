package com.f4blog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4Blog.basic.reqres.response.ResponseData;
import com.f4Blog.basic.web.controller.AbstractController;
import com.f4blog.admin.service.inter.RoleService;
import com.f4blog.model.base.BaseRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api("角色管理")
@RequestMapping("role")
@RestController
public class RoleController extends AbstractController {

    @Autowired
    RoleService roleService;

    @ApiOperation(value="分页查询", notes="查询角色",httpMethod="GET")
    @RequestMapping("query")
    public ResponseData query(@Param("page") Page page, String name) {
        Page page1= roleService.query(page, name);
        return ResponseData.success(page1);
    }

    @ApiOperation(value="添加或编辑", notes="",httpMethod="POST")
    @RequestMapping("add_or_edit")
    public ResponseData add_or_edit(@Valid BaseRole model, HttpServletRequest request) {
        RequestPara para= new RequestPara(request);
        roleService.addOrEdit(model,para);
        return ResponseData.success();
    }

    @ApiOperation(value="删除角色(不包括子角色)", notes="",httpMethod="POST")
    @RequestMapping("delete")
    public ResponseData delete(Integer[] ids) {
        roleService.delete(ids);
        return ResponseData.success();
    }

    @ApiOperation(value="删除角色(包括子角色)", notes="",httpMethod="POST")
    @RequestMapping("delete_chilren")
    public ResponseData delete_chilren(Integer[] ids) {
        roleService.delete(ids);
        return ResponseData.success();
    }


    @ApiOperation(value="获取所有", notes="",httpMethod="POST")
    @RequestMapping("getAll")
    public ResponseData getAll(HttpServletRequest request) {
        RequestPara para= new RequestPara(request);
        return ResponseData.success(roleService.getAll(para));
    }


    @RequestMapping("getRoleIdByUserId")
    public ResponseData getRoleIdByUserId(String userId) {
        return ResponseData.success(roleService.getRoleIdByUserId(userId));
    }

    @ApiOperation(value="保存角色的菜单信息", notes="",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Integer"),
            @ApiImplicitParam(name = "menuIds", value = "菜单id数组", dataType = "Integer[]")
    })
    @RequestMapping(value = "saveMenuInfo")
    public ResponseData saveMenuInfo( Integer roleId,Integer[] menuIds) {
        return ResponseData.success(roleService.saveMenuInfo(roleId,menuIds));
    }


    @RequestMapping("getById")
    public ResponseData getById( Integer id) {
        return ResponseData.success(roleService.getById(id));
    }

}

