package growth.bach.common.lang.spi;

import java.lang.annotation.*;

/**
 * 身份标识符
 * 同一个接口的不同子类标识符应保证唯一性
 * @author brain
 * @version 1.0
 * @date 2024/11/1 10:53
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Identity {
    String value();
}
