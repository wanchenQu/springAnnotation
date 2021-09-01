package com.chenTest.config;

import com.chenTest.bean.Person;
import com.chenTest.service.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Configuration
/**告诉Spring这是一个配置类*/
@ComponentScan(value = "com.chenTest", includeFilters = {
        //@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
        //@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
}, useDefaultFilters = false)
/**ComponentScan
 * value 控制要扫描的包
 * excludeFilters 控制不需要的
 * includeFilters 控制只包含谁*/
public class MainConfig {
    @Bean("person")
    /**把person注册进容器，id默认是方法名，可以自定义*/
    public Person person01() {
        return new Person("wangxie", 26);
    }
}
