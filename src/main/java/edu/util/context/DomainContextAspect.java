package edu.util.context;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 14:41
 */
public class DomainContextAspect {

    @Pointcut("execution(public  * edu.util.context..*Facade.*(..))")
    public void domainMethod(){

    }

    @Around("domainMethod()")
    public Object handle(ProceedingJoinPoint jp) throws Throwable{
        Object[] args = jp.getArgs();
        if (DomainContextHolder.getContext() != null){
            return jp.proceed(args);
        }

        try {
            if (args != null && args.length != 0){
                Arrays.stream(args).filter(it -> it instanceof BizType).findAny().ifPresent(b -> {
                    DomainContext domainContext = new DomainContext();
                    domainContext.setBizType((BizType) b);
                    DomainContextHolder.setContext(domainContext);
                });
            }
            return jp.proceed();
        }finally {
            DomainContextHolder.clearContext();
        }
    }
}
