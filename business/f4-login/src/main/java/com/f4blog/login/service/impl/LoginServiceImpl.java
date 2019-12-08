package com.f4blog.login.service.impl;

import com.f4Blog.basic.exception.Assert;
import com.f4Blog.basic.exception.ServiceException;
import com.f4Blog.basic.reqres.response.ResponseData;
import com.f4blog.login.service.inter.LoginService;
import com.f4blog.model.base.BaseUser;
import com.f4blog.model.cloud.CloudUrlConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String register(BaseUser model){
        ResponseData responseData= restTemplate.postForObject(CloudUrlConstant.admin+"/user/add_or_edit",model,ResponseData.class);
        Assert.isTrue(responseData);
        return "注册成功";
    }
}
