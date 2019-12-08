package com.f4blog.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * spring的初始化类,用于启动spring boot
 * @time 2018年10月11日10:03:44
 * @author authstr
 */
//开启事务管理
@EnableTransactionManagement
//eureka客户端
@EnableEurekaClient
@SpringBootApplication(scanBasePackages={"com.f4blog.*"})
//@EntityScan(basePackages={"com.authstr.*"})
@MapperScan("com.f4blog.*.mapper*")
public class ApplicationInit {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationInit.class, args);
	}
}
