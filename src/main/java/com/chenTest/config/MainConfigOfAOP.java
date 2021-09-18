package com.chenTest.config;

import com.chenTest.aop.LogAspects;
import com.chenTest.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP:【动态代理】
 *      指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式
 * 1、导入AOP模块，Spring aop spring-aspects
 * 2、定义一个业务处理类（MathCalculator）希望在业务逻辑运行的时候将日志进行打印（方法之前、方法运行结束、方法出现异常）
 * 3、定义一个日志切面类（LogAspects）切面类里面的方法需要动态感知MathCalculator.div运行到哪里，然后执行
 *      通知方法：
 *          前置通知(@Before)：logStart：目标方法div运行之前运行
 *          后置通知(@After)：logEnd：目标方法div运行结束之后运行 无论方法正常结束还是异常结束都调用
 *          返回通知(@AfterReturning)：logReturn：目标方法div正常返回之后运行
 *          异常通知(@AfterThrowing)：logException：目标方法div运行出现异常之后运行
 *          环绕通知(@Around)：动态代理，手动推进目标方法运行（joinPoint.proceed()）
 * 4、给切面类的目标方法标注何时运行（通知注解）
 * 5、将切面类和业务逻辑类（目标方法所在类）都加入到容器中
 * 6、必须告诉Spring哪个类是切面类（给切面类上加一个注解 @Aspect）
 * [7]、给配置类中加@EnableAspectJAutoProxy【开启基于注解的AOP模式】
 *    在Spring中有很对的@EnableXXX
 *
 * 三步：
 *      1）、将业务逻辑组件和切面类都加入到IOC容器中，告诉Spring那个是切面类@Aspect
 *      2）、在切面类上的每一个通知方法上标注通知注解，告诉Spring何时何地运行（切入表达式）
 *      3）、开启基于注解的AOP模式@EnableAspectJAutoProxy
 *
 *
 * AOP原理：【看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么】
 *     1）、@EnableAspectJAutoProxy是什么？
 *          @Import(AspectJAutoProxyRegistrar.class) 给容器中导入AspectJAutoProxyRegistrar组件
 *          AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar
 *          利用AspectJAutoProxyRegistrar自定义给容器中注册Bean
 *
 *          把AnnotationAwareAspectJAutoProxyCreator 注册到 internalAutoProxyCreator 中
 *          internalAutoProxyCreator = 把AnnotationAwareAspectJAutoProxyCreator
 *          [利用 @EnableAspectJAutoProxy 注解中的AspectJAutoProxyRegistrar 给容器中注册了一个把AnnotationAwareAspectJAutoProxyCreator]支持注解的AspectJ自动代理创建器
 *     2）、AnnotationAwareAspectJAutoProxyCreator：
 *          AnnotationAwareAspectJAutoProxyCreator
 *              -> AspectJAwareAdvisorAutoProxyCreator
 *                  -> AbstractAdvisorAutoProxyCreator
 *                      -> AbstractAutoProxyCreator
 *                          -> ProxyProcessorSupport,
 *                          implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *                          关注后置处理器（在bean初始化完成前后做事情）、自动装配BeanFactory
 *
 * AbstractAutoProxyCreator.setBeanFactory()
 * AbstractAutoProxyCreator由后置处理器
 *
 * AbstractAdvisorAutoProxyCreator重写了setBeanFactory()方法 -> initBeanFactory()
 *
 * AnnotationAwareAspectJAutoProxyCreator.initBeanFactory
 *
 * 流程：
 *      1：传入配置类、创建IOC容器
 *      2：注册配置类，调用refresh()方法刷新容器。
 *      3：通过registerBeanPostProcessors注册bean的后置处理器来方便拦截bean的创建
 *          a:	String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);
 *              先获取IOC容器已经定义了的需要创建对象的所有BeanPostProcessor
 *          b:  beanFactory.addBeanPostProcessor(new BeanPostProcessorChecker(beanFactory, beanProcessorTargetCount));
 *              给容器中假别的BeanPostProcessor
 *          c:  First, register the BeanPostProcessors that implement PriorityOrdered.
 *              优先注册实现了PriorityOrdered接口的BeanPostProcessor
 *          d:  Next, register the BeanPostProcessors that implement Ordered.
 *              再注册实现了Ordered接口的BeanPostProcessor
 *          e:  Now, register all regular BeanPostProcessors.
 *              最后注册没实现优先级接口的BeanPostProcessor
 *          f:  注册BeanPostProcessor， 实际上就是创建BeanPostProcessor对象，保存在容器中
 *              创建internalAutoProxyCreator的BeanPostProcessor【AnnotationAwareAspectJAutoProxyCreator】
 *                  1、创建Bean实例
 *                  2、populateBean：给Bean的各种属性赋值
 *                  3、initializeBean：初始化Bean
 *                      a、invokeAwareMethods：处理Aware接口的方法回调
 *                      b、applyBeanPostProcessorsBeforeInitialization：应用后置处理器的postProcessBeforeInitialization方法
 *                      c、invokeInitMethods：执行自定义的初始化方法
 *                      d、applyBeanPostProcessorsAfterInitialization：应用后置处理器的postProcessAfterInitialization方法
 *                  4、BeanPostProcessor【AnnotationAwareAspectJAutoProxyCreator】创建成功，并调用了initBeanFactory方法，有了aspectJAdvisorFactory和aspectJAdvisorsBuilder
 *          g： registerBeanPostProcessors -> beanFactory.addBeanPostProcessor(postProcessor)，把BeanPostProcessor注册到
 * =====================以上是AnnotationAwareAspectJAutoProxyCreator的创建过程=====================
 *              AnnotationAwareAspectJAutoProxyCreator 实现 InstantiationAwareBeanPostProcessor接口的后置处理器
 *      4、finishBeanFactoryInitialization(beanFactory); 完成BeanFactory初始化工作，创建剩下的单实例Bean
 *          a、遍历获取容器中所有的Bean， 通过getBean(beanName)方法依次创建对象
 *              getBean(beanName) -> doGetBean(name, null, null, false) -> getSingleton获取单实例Bean ->
 *          b、创建Bean
 *             【AnnotationAwareAspectJAutoProxyCreator在所有Bean创建之前会有一个拦截，因为他是InstantiationAwareBeanPostProcessor后置处理器会调用postProcessBeforeInstantiation】
 *              1、// Eagerly check singleton cache for manually registered singletons.先从缓存中获取当前Bean，如果能获取到，说明Bean是之前被创建过的
 * 		            Object sharedInstance = getSingleton(beanName); 直接包装使用，否则再创建，只要创建好的Bean都会被缓存起来，保证单实例
 * 		        2、createBean(beanName, mbd, args); 创建Bean方法  AnnotationAwareAspectJAutoProxyCreator会在任何Bean创建之前先尝试返回Bean的实例
 * 		            【BeanPostProcessor 是在Bean对象创建完成初始化前后调用的】
 * 		            【InstantiationAwareBeanPostProcessor 是在创建Bean实例之前先尝试使用后置处理器返回对象的】
 * 		            a、// Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.
 * 			            Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
 * 			            希望后置处理器能返回一个代理对象，如果能返回代理对象就使用，如果不能就继续
 * 			                1、后置处理器先尝试返回对象：applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 * 			                    bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 * 			                    拿到所有instantiationAware后置处理器，就执行后置处理器postProcessBeforeInstantiation方法
 * 					            if (bean != null) {
 * 						            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
 *                              }
 * 			        b、doCreateBean(beanName, mbdToUse, args); 真正去创建一个bean实例，流程同3-f
 *
 * 	AnnotationAwareAspectJAutoProxyCreator【InstantiationAwareBeanPostProcessor】的作用：
 * 	    1、每一个Bean创建之前、调用postProcessBeforeInstantiation()方法
 * 	        以 MathCalculator 和 logAspects 的创建为例
 * 	        a、判断当前Bean是否在advisedBeans中（保存了所有需要增强的Bean）其中的方法需要切面进行切入
 * 	        b、判断当前Bean是否是基础类型的: Advice Pointcut Advisor AopInfrastructureBean
 * 	            或者是不是切面isAspect，@Aspect注解标注
 * 	        c、是否需要跳过
 * 	            1、获取候选的增强器（鞋面里面的通知方法）包装成Advisor集合
 * 	            List<Advisor> candidateAdvisors = findCandidateAdvisors();
 * 	            每一个封装的通知方法的增强器是InstantiationModelAwarePointcutAdvisor
 * 	            判断每一个增强器是不是AspectJPointcutAdvisor类型，如果是返回true
 * 	    2、创建对象：调用postProcessAfterInitialization方法
 * 	        return wrapIfNecessary(bean, beanName, cacheKey);如果需要的情况下，包装
 * 	        a、获取当前Bean的所有增强器。封装到Object[] specificInterceptors中
 * 	            １、找到所有候选的的增强器（找哪些通知方法是需要切入当前Bean方法４的）
 * 	            ２、获取到能在当前Bean使用的
 * 	            ３、给增强器排序
 * 	        b、把当前Bean保存到advisedBeans中
 * 	        c、如果当前Bean需要增强，创建当前Bean的代理对象proxy
 * 	            1、获取所有的增强器（代理方法）Advisor[] advisors
 * 	            2、保存到proxyFactory中
 * 	        d、给容器中返回当前组件Spring cglib 增强了的代理对象
 * 	        e、以后容器中获取到的就是这个组件的代理对象，执行目标方法的的时候，代理对象就会执行通知方法的流程
 * 	    3、目标方法执行
 * 	        容器中保存了组件的代理对象（cgilib增强后的对象），这个对象里面保存了详细信息（比如增强器，目标对象，xxx）
 * 	        a、CgilibAopProxy.intercept()；拦截目标方法的执行
 * 	        b、根据ProxyFactory对象获取将要执行的目标方法的拦截器链：
 * 	            List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 *                  1、创建一个List<Object> interceptorList保存所有拦截器，长度为5
 *                      一个默认的ExposeInvocationInterceptor + 4个自定义增强其
 *                  2、遍历所有的增强器，将其转为MethodInterceptor -> Interceptor
 *                      registry.getInterceptors(advisor);
 *                          a、如果增强器是 MethodInterceptor，直接加到集合中
 *                          b、如果不是，使用AdvisorAdapter将增强器转化为 MethodInterceptor
 *                          转换完成后返回MethodInterceptor数组
 * 	        c、如果没有拦截器链，直接执行目标方法
 * 	            拦截器链（每一个通知方法又被包装为方法拦截器，利用MethodInterceptor机制）
 * 	        d、如果有拦截器链，把需要执行的目标对象，目标方法，拦截器链等信息放到一个CglibMethodInvocation对象中，
 * 	            并调用其proceed方法
 * 	        e、拦截器链的触发过程
 * 	            currentInterceptorIndex记录当前拦截器索引，索引从-1开始
 * 	            1、如果没有拦截器直接执行目标方法，或者拦截器的索引和拦截器数组-1大小一样
 * 	                也就是执行到最后一个拦截器，执行目标方法
 * 	                interceptorOrInterceptionAdvice从interceptorsAndDynamicMethodMatchers数组中
 * 	                拿到currentInterceptorIndex拦截器执行invoke方法
 * 	            2、链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回之后再执行
 * 	                拦截器链的机制，保证通知方法与目标方法的执行顺序
 * -----------------------------------
 * 总结：
 *      1、@EnableAspectJAutoProxy 开启AOP功能
 *      2、@EnableAspectJAutoProxy 会给容器中注册一个组件 AnnotationAwareAspectJAutoProxyCreator、
 *      3、AnnotationAwareAspectJAutoProxyCreator 是一个后置处理器
 *      4、容器的创建流程
 *          a、registerBeanPostProcessors() 注册后置处理器，创建 AnnotationAwareAspectJAutoProxyCreator 对象
 *          b、finishBeanFactoryInitialization() 初始化剩下的单实例Bean
 *              1、创建业务逻辑组件和切面组件
 *              2、AnnotationAwareAspectJAutoProxyCreator 后置处理器拦截组件创建过程
 *              3、组件创建完成之后，postProcessAfterInitialization 判断组件是否需要增强 wrapIfNecessary
 *                  是：切面的通知方法，包装成增强器（Advisor）给业务逻辑组件创建一个代理对象（默认cglib）
 *      5、执行目标方法
 *          a、代理对象执行目标方法
 *          b、CgilibAopProxy.intercept()
 *              1、得到目标方法的拦截器（Advisor增强器包装成拦截器MethodInterceptor）
 *              2、利用拦截器的链式机制，依次进去每一个拦截器进行执行
 *              3、效果：
 *                  方法正常执行：前置通知 -> 目标方法 -> 后置通知 -> 返回通知
 *                  方法异常执行：前置通知 -> 目标方法 -> 后置通知 -> 异常通知
 * */
@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAOP {

    @Bean
    public MathCalculator calculator() {
        return new MathCalculator();
    }

    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }
}
