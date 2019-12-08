package com.f4blog.login.controller;

import com.f4Blog.basic.reqres.response.ResponseData;
import com.f4Blog.basic.web.controller.AbstractController;
import com.f4blog.login.service.inter.LoginService;
import com.f4blog.model.base.BaseUser;
import com.f4blog.model.cloud.CloudUrlConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Controller
public class LoginController extends AbstractController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoginService loginService;

    @RequestMapping("/")
    public String login_page(){
        return "/login";
    }


    @RequestMapping("/register")
    @ResponseBody
    public ResponseData register(BaseUser model){
        return ResponseData.success(loginService.register(model));
    }

    @RequestMapping("/test")
    @ResponseBody
    public ResponseData register(){
        return ResponseData.success();
    }


}
