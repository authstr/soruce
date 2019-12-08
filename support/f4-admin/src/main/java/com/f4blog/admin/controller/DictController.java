package com.f4blog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4Blog.basic.reqres.response.ResponseData;
import com.f4Blog.basic.web.controller.AbstractController;
import com.f4blog.admin.service.inter.DictService;
import com.f4blog.admin.service.inter.UserService;
import com.f4blog.model.base.BaseDict;
import com.f4blog.model.base.BaseUser;
import com.f4blog.model.constant.BaseC;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("dict")
@RestController
public class DictController extends AbstractController {

    @Autowired
    DictService dictService;

    @RequestMapping("query")
    public ResponseData query(@Param("page") Page page,Integer type_id, String name) {
        Page page1=dictService.query(page,type_id, name);
        return ResponseData.success(page1);
    }

    @RequestMapping("add_or_edit")
    public ResponseData add_or_edit(@Valid BaseDict model,HttpServletRequest request) {
        RequestPara para= new RequestPara(request);
        dictService.addOrEdit(model,para);
        return ResponseData.success();
    }

    @RequestMapping("delete")
    public ResponseData delete(Integer[] ids) {
        dictService.delete(ids);
        return ResponseData.success();
    }

    @RequestMapping("getById")
    public ResponseData getById( Integer id) {
        BaseDict a=dictService.getById(id);
        return ResponseData.success(dictService.getById(id));
    }

    @RequestMapping("dict_enable")
    public ResponseData dict_enable(Integer[] ids) {
        dictService.enableOrDis(ids, BaseC.COMMON_STATUS_NORMAL);
        return ResponseData.success();
    }

    @RequestMapping("dict_disabled")
    public ResponseData dict_disable(Integer[] ids) {
        dictService.enableOrDis(ids, BaseC.COMMON_STATUS_DISABLED);
        return ResponseData.success();
    }




}

