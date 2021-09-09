package com.chenTest.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {


    // 使用@Value注解进行赋值
    // 1、基本赋值
    // 2、可以写SpEl #{}
    // 3、可以写${}获取配置文件中的值（在运行环境变量重中的值）
    @Value("张三")
    private String name;
    @Value("#{20-2}")
    private int age;
    @Value("${person.nickName}")
    private String nickName;

}
