package com.f4Blog.basic.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注入分页处理bean
 * @time 2019年10月12日16:13:17
 * @author authstr
 */
@Configuration
public class ConfigPageHelper {
    /**
     * my-batis-puls 分页配置
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }
}
