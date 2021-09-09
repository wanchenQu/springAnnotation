package com.chenTest.test;

import com.chenTest.config.MainConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_LifeCycle {

//    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
//
//    private void printBeans(AnnotationConfigApplicationContext applicationContext) {
//        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//        for (String s : beanDefinitionNames) System.out.println(s);
//    }
    @Test
    public void Test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
        System.out.println("IOC容器创建完成");
        // 关闭容器
        applicationContext.close();

    }
}
