package edu.muying.component.useraction;

import java.lang.annotation.*;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 11:13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Activity {
    /**
     * 该行动点属于哪个活动
     * activityId 活动名称
     * @return
     */
    String activityId() default "";
}
