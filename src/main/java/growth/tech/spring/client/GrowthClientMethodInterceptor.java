package growth.tech.spring.client;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:48
 */

import growth.tech.spring.properties.LogItem;
import growth.tech.spring.utils.LogUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * client调用公共逻辑处理
 */
@Slf4j
@AllArgsConstructor
public class GrowthClientMethodInterceptor implements InvocationHandler {

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
            // 异常向外透传
            throw t;
        }
    }

}
