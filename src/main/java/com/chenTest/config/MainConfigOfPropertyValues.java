package com.chenTest.config;

import com.chenTest.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 使用@PropertySource读取外部配置文件中的k-v保存到运行环境中
 * 加载完外部的配置文件以后使用${}配置文件的值
 * */
@PropertySource(value = {"classpath:/person.properties"}, encoding = "UTF-8")
@Configuration
public class MainConfigOfPropertyValues {


    @Bean
    public Person person() {
        return new Person();
    }
}
