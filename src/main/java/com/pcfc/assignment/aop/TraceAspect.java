package com.pcfc.assignment.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TraceAspect {

    private static final Logger log = LoggerFactory.getLogger(TraceAspect.class);

    @Before("appMethods()")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature method = (MethodSignature) joinPoint.getSignature();

        log.info(">> {}.{}() with method parameters = {}",
                method.getDeclaringType().getSimpleName(),
                method.getName(),
                Arrays.toString(joinPoint.getArgs())
        );
    }

    @Pointcut("execution(* com.pcfc.assignment.controller..*(..)) || execution(* com.pcfc.assignment.service..*(..))")
    public void appMethods() {}

    @AfterReturning(pointcut = "appMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        log.info("<< {}.{}() returned response = {}",
                method.getDeclaringType().getSimpleName(),
                method.getName(),
                result
        );
    }
}
