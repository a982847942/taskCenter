package growth.tech.spring.utils;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:43
 */

import edu.common.exception.CommonException;
import growth.tech.Context;
import growth.tech.spring.properties.LogItem;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 打印日志工具类
 *
 * @author lijiawei
 * @date 2024/07/26 16:51 星期五
 */
@Slf4j
public class LogUtils {

    /**
     * 打印日主
     *
     * @param method    方法
     * @param args      参数
     * @param logItem   日志参数
     * @param success   是否成功
     * @param startTime 开始时间
     * @param result    方法返回结果
     * @param ex        异常
     */
    public static void printLog(Method method, Object[] args, LogItem logItem,
                                boolean success, long startTime, Object result, Throwable ex) {
        try {
            String methodName = method.getName();
            String className = method.getDeclaringClass().getName();

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
                            .append(commonException.getCode())
                            .append(",")
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

            // 入参、出参
            if (args != null) {
                logStr.append(Arrays.stream(args)
                        .map(arg -> logItem.isPrintContext() || !Context.class.isAssignableFrom(arg.getClass())
                                ? "[" + arg + "]"
                                : "[Context(ignore)]")
                        .collect(Collectors.toList()));
            }
            logStr.append(" >>>>>>>>>> ");
            logStr.append("[")
                    .append(result)
                    .append("])");

            if (success) {
                log.info(logStr.toString());
            } else {
                log.error(logStr.toString(), ex);
            }
        } catch (Throwable e) {
            log.warn("LogUtils printLog error", e);
        }
    }

}
