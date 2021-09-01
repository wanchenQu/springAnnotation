package com.chenTest.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinuxCondition implements Condition {

    /**
     * ConditionContext 判断条件能使用的上下文（环境）
     * AnnotatedTypeMetadata 注释信息
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 判断是否为Linux系统
        // 1、 获取到ioc使用的beanFactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        // 2、 类加载器
        ClassLoader classLoader = context.getClassLoader();
        // 3、 获取当前环境信息
        Environment environment = context.getEnvironment();
        // 4、 获取到bean定义的注册类
        BeanDefinitionRegistry registry = context.getRegistry();
        boolean b = registry.containsBeanDefinition("person");
        String property = environment.getProperty("os.name");
        if (property.contains("linux") && b) return true;
        return false;
    }
}
