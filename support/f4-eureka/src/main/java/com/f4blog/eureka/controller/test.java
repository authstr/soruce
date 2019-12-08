package com.f4blog.eureka.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class test   {

    @RequestMapping("test")
    public String test() {
        System.out.println("ddd");
        return "dsd";
    }

}
