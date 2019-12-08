package com.f4blog.blog.controller;

import com.f4blog.model.cloud.CloudUrlConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class test {
    @Autowired
    RestTemplate restTemplate;

    /**
     * 实例化RestTemplate
     * @return
     */
//    @LoadBalanced
//    @Bean
//    public RestTemplate rest() {
//        return new RestTemplate();
//    }

    @RequestMapping("test")
    public Map test(){
        System.out.println("ddd");
        System.out.println(restTemplate);
        Map dd=restTemplate.getForObject(CloudUrlConstant.admin+"/test",Map.class);
        dd.put("dddd","4641654565641");
        return dd;
    }

//    @RequestMapping("test2")
//    public String d(){
//        BaseUser user = d.selectById(1);
//        System.out.println(user);
//        return "l";
//    }
//
//    @RequestMapping("test3")
//    public String ad(){
//        BaseUser user = d.selectById(1);
//        System.out.println(user);
//        return "l";
//    }
//
//    @RequestMapping("test4")
//    public String aer(){
//        BaseUser user = new BaseUser();
//        user.setUsername("55    ");
//        user.setPassword("989");
//        d.insert(user);
//        System.out.println(user.getId());
//
////        System.out.println(user);
//        return "5";
//    }
}
