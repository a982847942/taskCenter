package growth.tech.spring.common;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:27
 */

import edu.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 抽取公共逻辑
 *
 * @author lijiawei
 * @date 2024/07/12 11:42 星期五
 */
@Slf4j
public abstract class AbstractLogAspect {

    /**
     * 获取注解参数。先从类上获取，再从方法获取（方法的优先级高）
     *
     * @param pjp 切入点参数
     * @return 注解参数，走到这里一定非null
     */
    @SuppressWarnings("unchecked")
    protected <A extends Annotation> A getAnnotation(ProceedingJoinPoint pjp,
                                                     Class<? extends Annotation> annotationClass) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        A annotation = (A) AnnotationUtils.findAnnotation(signature.getMethod(), annotationClass);
        if (annotation != null) {
            return annotation;
        }

        return (A) AnnotationUtils.findAnnotation(signature.getDeclaringType(), annotationClass);
    }

    /**
     * 打印日志
     */
    protected void printLog(ProceedingJoinPoint pjp, boolean success, long startTime, LogStrategy logStrategy,
                            Object result, Throwable ex) {
        try {
            // 不打印任何日志
            if (logStrategy == LogStrategy.NONE) {
                return;
            }

            // 如果执行成功 && 不需要打印INFO，直接返回
            if (success && logStrategy != LogStrategy.ALL) {
                return;
            }

            MethodSignature signature = (MethodSignature) pjp.getSignature();
            String className = signature.getDeclaringType().getSimpleName();
            String methodName = signature.getMethod().getName();
            long costTime = System.currentTimeMillis() - startTime;

            // [className][methodName][success/fail][costTime] ([入参1][入参2]>>>>>>>>>>[返回值])
            StringBuilder logStr = new StringBuilder()
                    .append("[")
                    .append(className)
                    .append("][")
                    .append(methodName)
                    .append("][");

            // 异常
            if (success) {
                logStr.append("success");
            } else {
                if (ex == null) {
                    logStr.append("UNKNOWN");
                } else if (ex instanceof CommonException) {
                    CommonException commonException = (CommonException) ex;
                    logStr.append(ex.getClass().getSimpleName())
                            .append("(")
                            .append(commonException.getCodeDescription())
                            .append(")");
                } else {
                    logStr.append(ex.getClass().getSimpleName());
                }
            }

            // 耗时
            logStr.append("][")
                    .append(costTime)
                    .append("ms] (");

            // 入参
            if (pjp.getArgs() != null) {
                logStr.append(Arrays.stream(pjp.getArgs())
                        .map(arg -> "[" + arg + "]")
                        .collect(Collectors.toList()));
            }
            logStr.append(">>>>>>>>>>");
            // 出参
            logStr.append("[")
                    .append(result)
                    .append("])");

            if (success) {
                log.info(logStr.toString());
            } else {
                log.error(logStr.toString(), ex);
            }
        } catch (Throwable e) {
            // ignored
        }
    }

}
