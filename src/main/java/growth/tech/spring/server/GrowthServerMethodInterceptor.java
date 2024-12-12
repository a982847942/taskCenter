package growth.tech.spring.server;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:34
 */

import edu.common.exception.CommonException;
import edu.common.exception.CommonResultCodeEnum;
import growth.tech.Result;
import growth.tech.spring.properties.LogItem;
import growth.tech.spring.utils.LogUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 对外服务公共逻辑处理
 */
@AllArgsConstructor
@SuppressWarnings("DuplicatedCode")
public class GrowthServerMethodInterceptor implements InvocationHandler {

    /** 目标对象 spring bean */
    private Object targetObject;

    /** client 接口 */
    private Class<?> interfaceClass;

    /** 日志参数 */
    private LogItem logItem;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 非interfaceClass的方法不打日志，例如java.lang.Object的方法
        if (method.getDeclaringClass() != interfaceClass) {
            return method.invoke(targetObject, args);
        }
        // 跳过日志的方法
        if (logItem != null && logItem.getSkipMethodNames().contains(method.getName())) {
            return method.invoke(targetObject, args);
        }

        long startTime = System.currentTimeMillis();
        try {
            Object result = method.invoke(targetObject, args);
            LogUtils.printLog(method, args, logItem, true, startTime, result, null);
            return result;
        } catch (Throwable t) {
            LogUtils.printLog(method, args, logItem, false, startTime, null, t);
            return buildErrorResp(method, t);
        }
    }

    /**
     * 构造方法异常的返回结果
     *
     * @param method 方法
     * @param t      throwable
     * @return 异常Result
     */
    @SneakyThrows
    private Object buildErrorResp(Method method, Throwable t) {
        Result result = getResult(t);
        Class<?> returnType = method.getReturnType();
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
