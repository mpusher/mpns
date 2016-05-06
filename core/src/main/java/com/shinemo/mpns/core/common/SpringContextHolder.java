package com.shinemo.mpns.core.common;

import static org.apache.commons.lang.Validate.notEmpty;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
    	SpringContextHolder.context = context;
    }

    public static Object getSpringBean(String beanName) {
        notEmpty(beanName, "bean name is required");
        return context==null?null:context.getBean(beanName);
    }
    
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return context.getBean(beanName, clazz);
    }

    public static String[] getBeanDefinitionNames() {
        return context.getBeanDefinitionNames();
    }
    
}

