package com.f4blog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f4Blog.basic.reqres.request.RequestPara;
import com.f4Blog.basic.reqres.response.ResponseData;
import com.f4Blog.basic.web.controller.AbstractController;
import com.f4blog.admin.service.inter.DictService;
import com.f4blog.admin.service.inter.DictTypeService;
import com.f4blog.model.base.BaseDictType;
import com.f4blog.model.base.BaseUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("dict_type")
@RestController
public class DictTypeController extends AbstractController {

    @Autowired
    DictTypeService dictTypeService;

    @RequestMapping("query")
    public ResponseData query(@Param("page") Page page, String name, Integer status, Integer type) {
        Page page1=dictTypeService.query(page, name,  status,type);
        return ResponseData.success(page1);
    }

    @RequestMapping("add_or_edit")
    public ResponseData add_or_edit(@Valid BaseDictType model) {
        RequestPara para=new RequestPara();
        dictTypeService.addOrEdit(model,para);
        return ResponseData.success();
    }

    @RequestMapping("getById")
    public ResponseData getById( String id) {
        return ResponseData.success( dictTypeService.getById(id));
    }

    @RequestMapping("delete")
    public ResponseData delete(Integer[] ids) {
        dictTypeService.delete(ids);
        return ResponseData.success();
    }




}

