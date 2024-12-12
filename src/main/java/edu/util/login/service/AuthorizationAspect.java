package edu.util.login.service;

import edu.util.context.BizType;
import edu.util.context.DomainContext;
import edu.util.context.DomainContextHolder;
import edu.util.login.LoginRequired;
import edu.util.login.facade.auth.AuthContextHolder;
import edu.util.login.facade.auth.AuthContextImpl;
import edu.util.login.facade.auth.Authentication;
import edu.util.login.facade.auth.ContextHolderStrategyThreadLocalImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 16:28
 */
public class AuthorizationAspect {

    @Resource
    private List<AuthorizationFilter> filters;

    @Pointcut("@annotation(edu.util.login.LoginRequired)")
    private void annotationRpcMethod() {
    }

    public Object authority(ProceedingJoinPoint jp) throws Throwable {
        try {
            Object[] args = jp.getArgs();
            LoginRequired annotation = ((MethodSignature) jp.getSignature()).getMethod().getAnnotation(LoginRequired.class);
            createDomainContext(annotation);
            authenticate(annotation, args);
            return jp.proceed();
        } finally {
            DomainContextHolder.clearContext();
            AuthContextHolder.clearContext();
        }
    }

    private void authenticate(LoginRequired annotation, Object[] args) {
        Optional<AuthorizationFilter> filterOptional = filters.stream().filter(it -> it.support(annotation, args)).findFirst();
        if (!filterOptional.isPresent()) {
            throw new UnsupportedOperationException();
        }
        Authentication authority = filterOptional.get().authority(annotation, args);
        AuthContextHolder.setContext(new AuthContextImpl(authority));
    }

    private void createDomainContext(LoginRequired annotation) {
        BizType bizType = annotation.bizType();
        DomainContext context = new DomainContext();
        context.setBizType(bizType);
        DomainContextHolder.setContext(context);
    }
}
