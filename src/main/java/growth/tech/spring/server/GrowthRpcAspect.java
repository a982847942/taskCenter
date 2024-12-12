package growth.tech.spring.server;

import edu.common.exception.CommonException;
import edu.common.exception.CommonResultCodeEnum;
import growth.tech.Result;
import growth.tech.spring.common.AbstractLogAspect;
import growth.tech.spring.constants.AspectOrderConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static growth.tech.spring.constants.AspectOrderConstants.RPC_ASPECT_ORDER;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:25
 */
/**
 * RPC 接口层。统一异常处理、日志打印
 */
@Slf4j
@Aspect
@Component
@Order(RPC_ASPECT_ORDER)
public class GrowthRpcAspect extends AbstractLogAspect {

    /**
     * 统一处理RPC异常
     *
     * @param pjp 切入点参数
     * @return 方法返回结果
     * @throws Throwable Throwable
     */
    @Around("@annotation(com.xiaohongshu.growth.tech.spring.server.GrowthRpc) " +
            "|| @within(com.xiaohongshu.growth.tech.spring.server.GrowthRpc)")
    public Object handleException(ProceedingJoinPoint pjp) throws Throwable {
        GrowthRpc annotation = getAnnotation(pjp, GrowthRpc.class);
        // 理论上走到这里，annotation一定不为空
        if (annotation == null || !annotation.enable()) {
            return pjp.proceed();
        }

        long startTime = System.currentTimeMillis();
        try {
            Object result = pjp.proceed();
            printLog(pjp, true, startTime, annotation.logStrategy(), result, null);
            return result;
        } catch (Throwable t) {
            printLog(pjp, false, startTime, annotation.logStrategy(), null, t);
            return buildErrorResp(pjp, t);
        }
    }

    /**
     * 构造方法异常的返回结果
     *
     * @param jp 切入点参数
     * @param t  throwable
     * @return 异常Result
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException    IllegalAccessException
     */
    private Object buildErrorResp(ProceedingJoinPoint jp, Throwable t)
            throws InvocationTargetException, IllegalAccessException {
        Result result = getResult(t);
        Class<?> returnType = ((MethodSignature) jp.getSignature()).getReturnType();
        PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(returnType, "result");
        if (pd == null) {
            throw new RuntimeException("RPC接口中不含有result字段");
        }
        Method writeMethod = pd.getWriteMethod();
        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
            writeMethod.setAccessible(true);
        }

        Object resp = BeanUtils.instantiateClass(returnType);
        writeMethod.invoke(resp, result);
        return resp;
    }

    /**
     * 将异常转换为RPC Result{@link Result}
     *
     * @param t 异常
     * @return RPC Result
     */
    private Result getResult(Throwable t) {
        Result result = new Result();
        if (t instanceof CommonException) {
            CommonException commonException = (CommonException) t;
            result.setCode(commonException.getCode());
            result.setMessage(commonException.getMessage());
        } else {
            result.setCode(CommonResultCodeEnum.SYSTEM_ERROR.getCode());
            result.setMessage(CommonResultCodeEnum.SYSTEM_ERROR.getMessage());
        }
        result.setSuccess(false);
        return result;
    }

}