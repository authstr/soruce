package com.f4blog.login.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.f4Blog.basic.cloud.RestTemplateUtils;
import com.f4Blog.basic.exception.Assert;
import com.f4Blog.basic.exception.ServiceException;
import com.f4Blog.basic.reqres.response.ResponseData;
import com.f4Blog.basic.reqres.utils.WebUtils;
import com.f4blog.login.service.inter.LoginService;
import com.f4blog.model.base.BaseUser;
import com.f4blog.model.cloud.CloudUrlConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String register(@Nullable BaseUser model){
        String url = CloudUrlConstant.admin+"user/register";
        HttpEntity<MultiValueMap<String, String>> httpEntity=RestTemplateUtils.getRequestHttpEntity(model);
        ResponseEntity<ResponseData> result = restTemplate.exchange(url, HttpMethod.POST,httpEntity, ResponseData.class);
        ResponseData responseData=result.getBody();
        Assert.isTrue(responseData);
        return "注册成功";
    }
}
