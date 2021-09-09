package com.chenTest.config;

import com.chenTest.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * bean的生命周期：
 *      bean的创建--->初始化--->销毁的过程
 * 容器管理bean的生命周期
 * 我们可以自定义初始化和销毁方法，容器在bean进行到当前生命周期的色时候来调用我们自定义的初始化和销毁方法
 *
 * 构造（对象创建）
 *      单实例： 在容器启动的时候创建对象
 *      多实例： 在每次获取的时候创建对象
 * BeanPostProcessor.postProcessBeforeInitialization
 * 初始化：
 *      对象创建完成，并赋值好，调用初始化方法
 * BeanPostProcessor.postProcessAfterInitialization
 *
 *
 * BeanPostProcessor原理：
 * populateBean(beanName, mbd, instanceWrapper);
 * 之后
 * initializeBean{
 *    applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 *    invokeInitMethods(beanName, wrappedBean, mbd);执行自定义初始化方法
 *    applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 * }

 * 销毁：
 *      单实例： 容器关闭时销毁
 *      多实例： 容器不会管理多实例bean容器不会销毁，需要手动
 *
 * 遍历得到容器中所有的BeanPostProcessor挨个执行BeforeInitialization
 * 一旦返回null跳出for循环不会执行后续的BeanPostProcessor.postProcessBeforeInitialization
 *
 *
 * 1)、指定初始化和销毁方法
 *      通过Bean注解指定：@Bean(initMethod = "init", destroyMethod = "destroy")
 * 2)、通过让Bean实现InitializingBean（定义初始化逻辑）
 *              实现DisposableBean（定义销毁逻辑）
 * 3)、通过JSR250 注解作用与方法
 *     @PostConstruct 在bean创建完成并且属性赋值完成后执行初始化
 *     @PreDestroy 在容器销毁bean之前通知我们进行请清理工作
 * 4)、interface BeanPostProcessor 接口，bean的后置处理器
 *     在bean初始化前后进行一些处理工作
 *     postProcessBeforeInitialization 在初始化之前工作
 *     postProcessAfterInitialization 在初始化之后工作
 *
 * Spring底层对BeanPostProcessor的使用
 *     bean赋值，注入其他组件 @Autowired 生命周期注解功能 @Async
 * */

@Configuration
@ComponentScan("com.chenTest.bean")
public class MainConfigOfLifeCycle {

    // @Scope("prototype")
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Car car() {
        return new Car();
    }
}
