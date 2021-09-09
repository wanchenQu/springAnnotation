package com.chenTest.test;

import com.chenTest.bean.Person;
import com.chenTest.config.MainConfigOfPropertyValues;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class IOCTest_PropertyValues {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);

    private void printBeans(AnnotationConfigApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String s : beanDefinitionNames) System.out.println(s);
    }

    @Test
    public void Test01() {
        printBeans(applicationContext);
        Person person = (Person) applicationContext.getBean("person");
        System.out.println("----------" + person);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("person.nickName");
        System.out.println("------------" + property);
        applicationContext.close();
    }
}
