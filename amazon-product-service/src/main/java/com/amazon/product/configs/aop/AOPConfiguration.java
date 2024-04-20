package com.amazon.product.configs.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AOPConfiguration {

    Logger log = LogManager.getLogger(AOPConfiguration.class);

    @Around("@annotation(ProductLogger)")
    public Object addThreadContext(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Before Method Invocation => " + proceedingJoinPoint.getSignature());
       /* if (ThreadContext.get("requestId") == null) {
            log.info("ThreadContext is not Initialized");
        } else {
            log.info("ThreadContext is Initialized with => " + ThreadContext.get("requestId"));
        }*/

        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        log.info("Result Received! Method => " + proceedingJoinPoint.getSignature() + " took => " + (System.currentTimeMillis() - startTime));
        return result;
    }
}
