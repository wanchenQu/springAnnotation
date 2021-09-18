package com.chenTest.test;

import com.chenTest.aop.MathCalculator;
import com.chenTest.config.MainConfigOfAOP;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_AOP {


    @Test
    public void Test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
        System.out.println("IOC容器创建完成");

        /**
         * 1、不要自己创建对象
         * */
        MathCalculator bean = applicationContext.getBean(MathCalculator.class);
        bean.div(1, 1);

        // 关闭容器
        applicationContext.close();

    }
}
