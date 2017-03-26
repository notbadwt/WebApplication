package com.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
public class ServiceLoggerAspect {

    private static Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Pointcut("execution(* com.application.service.*.*(..))")
    public void process() {
    }

    @Around("process()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            logger.debug("getKind(): " + joinPoint.getKind());
            logger.debug("getArgs():" + Arrays.toString(joinPoint.getArgs()));
            logger.debug("getTarget():" + joinPoint.getTarget().getClass().getName());
            result = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        return result;

    }

}
