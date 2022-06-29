package com.digiwin.app.kbs.service.configuration;

import com.digiwin.app.kbs.service.common.constant.ModuleConstant;
import com.digiwin.app.module.spring.DWModuleSpringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @ClassName GetBeanUtil
 * @Description TODO
 * @Author author
 * @Date 2021/11/11 16:51
 * @Version 1.0
 **/
public class SpringContextHolder{

//    public static ApplicationContext getBean(String beanName) {
//      return DWModuleSpringUtils.getModuleSpringContext(ModuleConstant.MODULE_NAME).getBean(beanName);
//    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return DWModuleSpringUtils.getModuleSpringContext(ModuleConstant.MODULE_NAME).getBean(beanName, clazz);
    }

//    public static <T> T getBean(Class<T> bean) {
//        assertApplicationContext();
//        return applicationContext.getBean(bean);
//    }
//
//
//    private static void assertApplicationContext() {
//        if (SpringContextHolder.applicationContext == null) {
//            throw new RuntimeException("applicationContext属性为null,请检查是否注入了SpringContextHolder!");
//        }
//    }
}
