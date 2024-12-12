package edu.util.login.handle;

import edu.util.context.BizType;

import java.lang.annotation.*;

/**
 * 领域请求处理器
 * @author brain
 * @version 1.0
 * @date 2024/10/26 19:34
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReqHandler {


    /**
     * 可以处理的业务集合
     * @return
     */
    BizType[] bizType() default {};

    /**
     * 支持的请求类型
     * @return
     */
    Class<?> support() default Object.class;
}
