package growth.tech.spring.server;

import growth.tech.spring.common.LogStrategy;

import java.lang.annotation.*;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:20
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GrowthRpc {

    /**
     * 是否开启注解功能，一般用于打在方法上来覆盖类上的注释
     * 设置为false，则相当于执行对应方法时跳过切面逻辑，直接执行原有逻辑
     * @return
     */
    boolean enable() default true;

    /**
     * 打印日志的策略，默认打印完整出入参日志，无论成功/失败
     * @return
     */
    LogStrategy  logStrategy() default LogStrategy.ALL;

}
