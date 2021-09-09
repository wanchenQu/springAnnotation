package com.chenTest.condition;

import com.chenTest.bean.Rainbow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {


    /**
     * AnnotationMetadata: 当前类的注解信息
     * BeanDefinitionRegistry: Bean定义的注册类
     *      把所有需要添加到容器中的bean，调用registerBeanDefinitions方法手动注册
     * */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean b = registry.containsBeanDefinition("com.chenTest.bean.Red");
        boolean b1 = registry.containsBeanDefinition("com.chenTest.bean.Blue");
        // 指定bean的定义信息
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Rainbow.class);
        // 注册一个bean，指定bean名
        if (b && b1) registry.registerBeanDefinition("rainBow", rootBeanDefinition);
    }
}
