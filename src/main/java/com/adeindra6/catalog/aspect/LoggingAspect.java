package com.adeindra6.catalog.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Aspect
public class LoggingAspect {

    @Pointcut("execution(* com.adeindra6.catalog.web.*.*(..))")
    private void restAPI() {}

    @Pointcut("within(com.adeindra6.catalog.web.*)")
    private void withinPointcutExample() {}

    @Pointcut("args(com.adeindra6.catalog.dto.PublisherCreateRequestDTO)")
    private void argsPointcutExample() {}

    @Pointcut("@args(com.adeindra6.catalog.annotation.LogThisArg)")
    private void argsAnnotationPointcutExample() {}

    @Pointcut("@annotation(com.adeindra6.catalog.annotation.LogThisMethod)")
    private void annotationPointcutExample() {}
    
    @Before("annotationPointcutExample()")
    public void beforeExecutedLogging() {
        log.info("this is log from aspect before method executed");
    }

    @After("annotationPointcutExample()")
    public void afterExecutedLogging() {
        log.info("this is log from aspect after method executed");
    }

    @AfterReturning("annotationPointcutExample()")
    public void afterReturningExecutedLogging() {
        log.info("this is log from aspect after returning method executed");
    }

    @AfterThrowing("annotationPointcutExample()")
    public void afterThrowingExecutedLogging() {
        log.info("this is log from aspect after throwing method executed");
    }

    @Around("restAPI()")
    public Object processingTimeLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        try {
            log.info("Start {}.{}", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
            stopWatch.start();
            return joinPoint.proceed();
        }
        finally {
            stopWatch.stop();
            log.info("Finish {}.{} execution time: {}", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());
        }
    }
}
