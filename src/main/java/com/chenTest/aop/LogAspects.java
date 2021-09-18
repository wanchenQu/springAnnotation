package com.chenTest.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;


/**
 * 切面类
 *
 * @Aspect 告诉Spring当前类是一个切面类
 */
@Aspect
public class LogAspects {

    /**
     * 抽取公共的切入点表达式
     * 1、本类引用
     * 2、其他的勤勉引用
     */
    @Pointcut("execution(public int com.chenTest.aop.MathCalculator.*(..))")
    public void pointCut() {
    }

    ;

    // @Before在目标方法之前切入， 切入点表达式（指定在哪个方法切入）
    @Before("pointCut()")
    private void logStart(JoinPoint joinpoint) {
        Object[] args = joinpoint.getArgs();
        System.out.println("" + joinpoint.getSignature().getName() + "@Before 除法运行------参数列表是：{" + Arrays.asList(args) + "}");
    }

    @After("com.chenTest.aop.LogAspects.pointCut()")
    private void logEnd(JoinPoint joinpoint) {
        System.out.println("" + joinpoint.getSignature().getName() + "@After 除法结束");
    }

    /**
     * JoinPoint joinpoint 一定要出现在参数表的第一位
     */
    @AfterReturning(value = "pointCut()", returning = "result")
    private void logReturn(JoinPoint joinpoint, Object result) {
        System.out.println("" + joinpoint.getSignature().getName() + "@AfterReturning 除法正常返回---------计算结果是：{" + result + "}");
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    private void logException(JoinPoint joinpoint, Exception exception) {
        System.out.println("" + joinpoint.getSignature().getName() + "@AfterThrowing 除法异常---------异常信息是：{" + exception + "}");
    }
}
