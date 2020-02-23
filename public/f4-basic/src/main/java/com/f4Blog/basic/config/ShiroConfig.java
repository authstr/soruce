package com.f4Blog.basic.config;

import com.f4Blog.basic.shiro.ShiroRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Shiro配置类
 */
@Configuration
public class ShiroConfig {
    /**
     *  创建shiro的过滤器工厂bean
     * @return
     */
     public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){
         ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
         //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

         return shiroFilterFactoryBean;
     }

    /**
     * 创建默认的 安全管理类
     * @return
     */
     @Bean("defaultWebSecurityManager")
     public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("shiroRealm") ShiroRealm shiroRealm){
         DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
         //关联Realm
         securityManager.setRealm(shiroRealm);
         return securityManager;
     }

    /**
     * 创建Realm
     * @return
     */
    @Bean("shiroRealm")
     public ShiroRealm getShiroRealm(){
         return new ShiroRealm();
     }
}
