package com.chenTest.test;

import com.chenTest.bean.Blue;
import com.chenTest.bean.Person;
import com.chenTest.config.MainConfig;
import com.chenTest.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;


public class IOCTest {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

    private void printBeans(AnnotationConfigApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String s : beanDefinitionNames) System.out.println(s);
    }

    @Test
    public void test01() {
        printBeans(applicationContext);
    }

    @Test
    public void test02() {
        System.out.println("IOC容器创建完成");
        printBeans(applicationContext);
        Object person1 = applicationContext.getBean("person");
        Object person2 = applicationContext.getBean("person");
        System.out.println(person1 == person2);
    }

    @Test
    public void test03() {
        printBeans(applicationContext);
        Map<String, Person> beansOfType = applicationContext.getBeansOfType(Person.class);
        System.out.println(beansOfType);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("os.name");
        // 动态获取环境变量的值
        System.out.println(property);
    }

    @Test
    public void testImport() {
        printBeans(applicationContext);
        Blue bean = applicationContext.getBean(Blue.class);
        System.out.println(bean);

        // 工厂bean获取的是FactoryBean中getObject方法创建的对象
        Object colorFactoryBean1 = applicationContext.getBean("colorFactoryBean");
        Object colorFactoryBean2 = applicationContext.getBean("colorFactoryBean");
        // 默认获取FactoryBean中getObject方法创建的对象
        // 通过在id前加&标识，获取工厂Bean本身
        Object colorFactoryBean3 = applicationContext.getBean("&colorFactoryBean");

        System.out.println("---------colorFactoryBean1类型" + colorFactoryBean1.getClass());
        System.out.println("---------colorFactoryBean2类型" + colorFactoryBean2.getClass());
        System.out.println("---------colorFactoryBean3类型" + colorFactoryBean3.getClass());

        System.out.println(colorFactoryBean1 == colorFactoryBean2);
    }

}
