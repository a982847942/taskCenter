package growth.tech.spring.client;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:47
 */

import growth.tech.spring.common.LogStrategy;

import java.lang.annotation.*;

/**
 * RPC客户端切面，统一打印调下游的出入参数、做监控等
 * <p>
 * {@link GrowthClientAspect}
 *
 * 这个注解后面会删除，client打日志请使用starter的方式。
 *
 * @author lijiawei
 * @date 2024/07/12 10:39 星期五
 */
@Deprecated
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GrowthClient {

    /**
     * 生效开关，一般用于打在方法上来覆盖类上的注解<p>
     * 设置为false，则相当于执行对应方法时跳过切面逻辑，直接执行原有逻辑。
     *
     * @return 默认生效
     */
    boolean enable() default true;

    /**
     * 打印日志的策略，默认打印完整出入参日志。无论成功/失败
     *
     * @return 打印日志策略，默认打印完整出入参日志。无论成功/失败
     */
    LogStrategy logStrategy() default LogStrategy.ALL;

}
