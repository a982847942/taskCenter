package growth.bach.common.lang.spi;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/1 15:29
 */
public class IdentityKits {
    public static String getIdentity(Object object){
        Identity annotation = object.getClass().getAnnotation(Identity.class);
        if (annotation == null){
            // 错误 日志 异常
        }
        return annotation.value();
    }
}
