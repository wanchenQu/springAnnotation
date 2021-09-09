package com.chenTest.config;

import com.chenTest.bean.Car;
import com.chenTest.bean.Color;
import com.chenTest.dao.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 自动装配：
 * Spring利用依赖注入（DI）完成对IOC容器中各个组件的依赖关系赋值
 * 1) @Autowired 自动注入
 *      a、默认按照类型去容器中找对应的组件，找到就进行赋值 applicationContext.getBean(BookService.class);
 *      b、如果找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找 applicationContext.getBean(”bookDao“);
 *      c、@Qualifier("bookDao")使用@Qualifier指定需要装配的组件id，而不是使用属性名
 *      d、自动装配默认一定要将属性赋值好，没有就会报错
 *          可以使用@Autowired(required = false)
 *      e、@Primary，让Spring进行自动装配的时候默认使用首选的bean
 *      BookService {
 *          @Autowired
 *          BookDao bookDao
 *      }
 * 2) Spring还支持使用@Resource(JSR250)和@Inject(JSR330)[java规范的注解]
 *      @Resource
 *          可以和@Autowired一样实现自动装配功能，默认是按照组件名称进行装配的
 *          没有能支持@Primary功能没有支持@Autowired(required = false)功能
 *      @Inject
 *          需要导入javax.injex依赖，@Autowired功能一致，(required = false)无属性设置
 *
 *    @Autowired是Spring定义的 @Resource @Inject是Java规范
 *
 * 3）@Autowired可以标注在：构造器、参数、方法、属性上 都是从容器中获取参数组件的值
 *      a、标注在方法位置  @Bean+方法参数，参数从容器中获取，默认不写@Autowired效果是一样的，都能自动装配
 *      b、标注在构造器上 如果组件只有一个有参构造器，这个有参构造器@Autowired可以省略，参数位置的组件还是可以自动从容其中获取
 *      c、标注在参数上
 *
 * 4）自定义组件想要使用Spring容器底层的一些组件（ApplicationContext， BeanFactory， XXX）
 *      自定义组件时间XXXAware，在创建对象的时候，会调用接口规定的方法注入相关组件
 *      把Spring底层组件注入到自定义的Bean中
 *      ApplicationContextAware 由 ApplicationContextAwareProcessor处理
 */

@Configuration
@ComponentScan({"com.chenTest.controller", "com.chenTest.dao", "com.chenTest.service", "com.chenTest.bean"})
public class MainConfigOfAutowired {

    @Bean("bookDao2")
    @Primary
    public BookDao bookDao() {
        BookDao bookDao = new BookDao();
        bookDao.setLabel("2");
        return bookDao;
    }

    /**
     * @Bean 标注的方法创建对象，方法参数的值从容器中获取
     * */
    @Bean
    public Color color(/**@Autowired*/Car car) {
        Color color = new Color();
        color.setCar(car);
        return color;
    }
}
