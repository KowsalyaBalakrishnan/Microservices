package com.amazon.order.config.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class AOPConfiguration {

    Logger log = LogManager.getLogger(AOPConfiguration.class);

    @Around("@annotation(OrderLogger)")
    public Object computeExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        log.info("Execution time for took {} ms ", System.currentTimeMillis() - startTime);
        return proceed;
    }
}
