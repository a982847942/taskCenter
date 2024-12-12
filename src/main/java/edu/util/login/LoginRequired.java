package edu.util.login;

import edu.util.context.BizType;

import java.lang.annotation.*;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 16:10
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
    /**
     * 具体的登录技术
     * @return "mini" / "porch"
     */
    String value() default "mini";

    /**
     * 业务
     * @return
     */
    BizType bizType();
}
