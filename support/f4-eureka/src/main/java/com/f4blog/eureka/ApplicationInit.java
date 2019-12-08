package com.f4blog.eureka;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * spring的初始化类,用于启动spring boot
 *
 * @author authstr
 * @time 2018年10月11日10:03:44
 */
//开启事务管理
@EnableTransactionManagement
//eureka服务端
@EnableEurekaServer
@SpringBootApplication(scanBasePackages = {"com.f4blog.*"})
@MapperScan("com.f4blog.*.mapper*")
public class ApplicationInit {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationInit.class, args);
    }
}
