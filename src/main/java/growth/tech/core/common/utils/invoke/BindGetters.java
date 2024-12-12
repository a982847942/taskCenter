package growth.tech.core.common.utils.invoke;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 15:17
 */

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.lang.invoke.*;

/**
 * @author lijiawei
 * @version BindGetters.java, v 0.1 2023年01月30日 11:53 AM lijiawei
 */
class BindGetters {

    /** getter函数前缀 */
    private static final String GET_PREFIX = "get";

    /** is函数前缀 */
    private static final String IS_PREFIX = "is";

    /**
     * 获取字段或方法绑定的getter
     *
     * @param clazz      目标类
     * @param field      字段
     * @param returnType 返回类型
     * @param <V>        值类型
     * @return getter
     */
    @SneakyThrows
    static <T, V> BindGetter<T, V> field(Class<T> clazz, String field, Class<V> returnType) {
        MethodHandle methodHandle = findGetterMethod(clazz, field, returnType);
        return lambda(methodHandle);
    }

    /**
     * 获取字段或方法绑定的getter
     *
     * @param clazz         目标类
     * @param fieldOrMethod 字段或者方法
     * @param returnType    返回类型
     * @param <V>           值类型
     * @return getter
     */
    @SneakyThrows
    static <T, V> BindGetter<T, V> fieldOrMethod(Class<T> clazz, String fieldOrMethod, Class<V> returnType) {
        try {
            MethodHandle methodHandle = findGetterMethod(clazz, fieldOrMethod, returnType);
            return lambda(methodHandle);
        } catch (IllegalAccessException | NoSuchMethodException ignore) {
            MethodHandle methodHandle = MethodHandles.publicLookup().findVirtual(clazz, fieldOrMethod,
                    MethodType.methodType(returnType));
            return lambda(methodHandle);
        }
    }

    /**
     * 生成lambda
     *
     * @param virtual 虚拟方法
     * @param <T>     对象泛型
     * @param <V>     字段泛型
     * @return 结果
     */
    @SuppressWarnings("unchecked")
    private static <T, V> BindGetter<T, V> lambda(MethodHandle virtual) throws Throwable {
        CallSite callSite = LambdaMetafactory.metafactory(
                MethodHandles.lookup(),
                "get",
                MethodType.methodType(BindGetter.class),
                MethodType.methodType(Object.class, Object.class),
                virtual,
                virtual.type()
        );
        return (BindGetter<T, V>) callSite.getTarget().invoke();
    }

    /**
     * 获取getter函数
     *
     * @param clazz     类
     * @param field     字段
     * @param fieldType 字段类型
     * @return 对应的方法
     */
    private static MethodHandle findGetterMethod(Class<?> clazz, String field, Class<?> fieldType)
            throws NoSuchMethodException, IllegalAccessException {
        String methodName;
        if (fieldType == boolean.class) {
            methodName = IS_PREFIX + StringUtils.capitalize(field);
        } else {
            methodName = GET_PREFIX + StringUtils.capitalize(field);
        }
        return MethodHandles.publicLookup().findVirtual(clazz, methodName, MethodType.methodType(fieldType));
    }
}
