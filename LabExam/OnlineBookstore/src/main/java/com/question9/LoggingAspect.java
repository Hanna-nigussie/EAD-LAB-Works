package com.question9;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut expression to intercept all method calls in the com.labexam.books package
    @Before("execution(* com.labexam.books.*.*(..))")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        // Get method name
        String methodName = joinPoint.getSignature().getName();
        // Get class name
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        // Get method arguments
        Object[] args = joinPoint.getArgs();
        StringBuilder argsStr = new StringBuilder();
        for (Object arg : args) {
            if (arg != null) {
                argsStr.append(arg.toString()).append(", ");
            } else {
                argsStr.append("null, ");
            }
        }
        
        // Log method name, class name, and method arguments
        logger.info("Method {} in class {} is about to be called with arguments [{}]", methodName, className, argsStr.toString());
    }
}
