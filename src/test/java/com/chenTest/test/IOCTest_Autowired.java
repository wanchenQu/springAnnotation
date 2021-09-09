package com.chenTest.test;

import com.chenTest.bean.Boss;
import com.chenTest.bean.Car;
import com.chenTest.bean.Color;
import com.chenTest.config.MainConfigOfAutowired;
import com.chenTest.dao.BookDao;
import com.chenTest.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Autowired {

    @Test
    public void Test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);
        System.out.println("IOC容器创建完成" + applicationContext);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String s : beanDefinitionNames) System.out.println(s);
        System.out.println("------------------------------");
        BookService bean = applicationContext.getBean(BookService.class);
        System.out.println(bean.toString());

//        BookDao bean1 = applicationContext.getBean(BookDao.class);
//        System.out.println(bean1);
        Boss bean1 = applicationContext.getBean(Boss.class);
        System.out.println(bean1.toString());
        Car bean2 = applicationContext.getBean(Car.class);
        System.out.println(bean2);

        Color bean3 = applicationContext.getBean(Color.class);
        System.out.println(bean3.toString());

        // 关闭容器
        applicationContext.close();
    }
}
