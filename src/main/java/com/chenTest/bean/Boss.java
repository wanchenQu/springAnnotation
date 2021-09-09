package com.chenTest.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
// 默认加在IOC容器中的组件，容器启动会调用无参构造器创建操作，再进行初始化赋值等操作

public class Boss {

    //@Autowired
    public Boss(@Autowired Car car) {
        System.out.println("---------通过Boss有参构造器创建对象");
        this.car = car;
    }

    private Car car;

    public Car getCar() {
        return car;
    }

    //@Autowired
    // 标注在方法上，Spring容器创建当前对象时，就会调用当前方法，完成赋值
    // 方法使用的参数，自定义类型的值从IOC容器中获取
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}
