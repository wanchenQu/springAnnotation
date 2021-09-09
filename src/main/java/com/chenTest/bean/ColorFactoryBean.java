package com.chenTest.bean;

import org.springframework.beans.factory.FactoryBean;

// 创建一个Spring定义的FactoryBean
public class ColorFactoryBean implements FactoryBean<Color> {

    // 返回一个Color对象，这个对象会添加到容器中
    @Override
    public Color getObject() throws Exception {
        System.out.println("-------------ColorFactoryBean");
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }


    // ture : bean是单实例的，容器中只保存一个
    // false : bean是多实例的，每次获取都会通过getObject()方法创建一个新的bean
    @Override
    public boolean isSingleton() {
        return false;
        //return FactoryBean.super.isSingleton();
    }
}
