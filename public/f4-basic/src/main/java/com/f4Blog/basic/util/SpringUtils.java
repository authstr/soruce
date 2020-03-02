package com.f4Blog.basic.util;

import com.f4Blog.basic.exception.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SpringUtils implements ApplicationContextAware {
    private static Log log = LogFactory.getLog(SpringUtils.class);
    private static ApplicationContext applicationContext;

    @Override
    public  void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }

    public static Environment getEnvironment(){
        return SpringUtils.getContext().getEnvironment();
    }

    public static Object getBean(String beanId) {
        assertApplicationContext();
        return SpringUtils.getBean(Object.class, beanId);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    public static <T> T getBean(Class<T> clazz, String beanId) throws ClassCastException {
        ApplicationContext context = SpringUtils.getContext();
        Assert.isTrue(StringUtils.hasText(beanId), "beanId must not null!",true);
        boolean a=context.containsBean(beanId);
        Assert.isTrue(context.containsBean(beanId), "beanId :[" + beanId + "] is not exist!",true);
        Object bean = null;
        bean = context.getBean(beanId);
        return (T)bean;
    }

    private static void assertApplicationContext() {
        if (applicationContext == null) {
            throw new RuntimeException("applicaitonContext属性为null,请检查是否注入了SpringUtils!");
        }
    }
}

