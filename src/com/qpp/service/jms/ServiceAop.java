package com.qpp.service.jms;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

//@Component
@Aspect
public class ServiceAop {
    @Pointcut("execution(* com.qpp.service.common..*(..))")
    public void anyMethod(){
    }

    @Before("anyMethod()")
    public void doAccessCheck(){
        System.out.println("Before action:");
    }
    @Around("anyMethod()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("进入环绕通知");
        Object object = pjp.proceed();//执行该方法
        System.out.println("退出方法");
        return object;
    }
}
