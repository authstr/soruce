package com.f4Blog.basic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * spring的初始化类,用于启动spring boot
 * @time 2018年10月11日10:03:44
 * @author authstr
 */
@SpringBootApplication(scanBasePackages={"com.f4Blog.*"})
@MapperScan("com.f4blog.*.mapper*")
public class ApplicationInit {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationInit.class, args);
	}
}
