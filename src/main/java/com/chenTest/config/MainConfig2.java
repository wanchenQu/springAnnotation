package com.chenTest.config;

import com.chenTest.bean.Color;
import com.chenTest.bean.ColorFactoryBean;
import com.chenTest.bean.Person;
import com.chenTest.bean.Red;
import com.chenTest.condition.LinuxCondition;
import com.chenTest.condition.MyImportBeanDefinitionRegistrar;
import com.chenTest.condition.MyImportSelector;
import com.chenTest.condition.WindowsCondition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

// @Conditional({WindowsCondition.class})
// 满足当前条件，这个类中配置的所有bean才会注册到容器中
@Configuration
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class}) // 导入组件，id默认是组件全路径
public class MainConfig2 {
    /**
     * Specifies the name of the scope to use for the annotated component/bean.
     * Defaults to an empty string ("") which implies SCOPE_SINGLETON.
     * Since:
     * 4.2
     * See Also:
     * ConfigurableBeanFactory.SCOPE_PROTOTYPE, 多实例的, IOC容器启动不会调用方法，每次获取的时候都会调用方法
     * ConfigurableBeanFactory.SCOPE_SINGLETON, 单实例的, IOC容器启动会调用方法创建对象放到IOC容器中 以后获取直接从容器中拿
     * org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST,
     * org.springframework.web.context.WebApplicationContext.SCOPE_SESSION,
     * 懒加载：
     * 单实例模式下，容器启动时不调用方法，第一次使用获取bean的时候创建对象并初始化dd
     */
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Lazy
    @Bean
    /**把person注册进容器，id默认是方法名，可以自定义*/
    public Person person() {
        System.out.println("---------------给容器中添加Person");
        return new Person("wangxie2", 26, "11");
    }

    /**
     * @Conditional, 按照一定的条件惊醒判断，满足条件给容器中注册bean
     * Windows注册bill Linux注册linus
     */
    @Conditional({WindowsCondition.class})
    @Bean("bill")
    public Person person01() {
        System.out.println("---------------给容器中添加 bill gates");
        return new Person("bill gates", 62, "11");
    }

    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person person02() {
        System.out.println("---------------给容器中添加 linus");
        return new Person("linus", 50, "11");
    }

    /**
     * 给容器中注册组件:
     * 1、包扫描+组件标注注解(@Controller, @Service, @Repository, @Component)
     * 2、@Bean 导入第三方包里面的组件
     * 3、@Import 快速给容器中导入组件
     * a、@Import 导入组件，id默认是组件全路径
     * b、ImportSelector 接口
     * Select and return the names of which class(es) should be imported based on
     * the AnnotationMetadata of the importing @Configuration class.
     * Returns:
     * the class names, or an empty array if none
     * c、ImportBeanDefinitionRegistrar
     * 4、使用Spring提供的FactoryBean(工厂Bean)
     * 默认获取FactoryBean中getObject方法创建的对象
     * 通过在id前加&标识，获取工厂Bean本身
     */

    @Bean
    public ColorFactoryBean colorFactoryBean() {
        return new ColorFactoryBean();
    }
}
