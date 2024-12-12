package growth.tech.core.common.utils.invoke;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 15:16
 */
/**
 * @author lijiawei
 * @version BindGetter.java, v 0.1 2023年01月30日 11:53 AM lijiawei
 */
@FunctionalInterface
public interface BindGetter<T, V> {

    /**
     * 常量getter，既getter时和对象无关
     *
     * @param value 常量值
     * @param <V>   值类型
     * @return getter
     */
    static <T, V> BindGetter<T, V> constant(V value) {
        return o -> value;
    }

    /**
     * 获取字段或方法绑定的getter
     *
     * @param clazz     目标类
     * @param field     字段
     * @param fieldType 字段类型
     * @param <V>       值类型
     * @return getter
     */
    static <T, V> BindGetter<T, V> field(Class<T> clazz, String field, Class<V> fieldType) {
        return BindGetters.field(clazz, field, fieldType);
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
    static <T, V> BindGetter<T, V> fieldOrMethod(Class<T> clazz, String fieldOrMethod, Class<V> returnType) {
        return BindGetters.fieldOrMethod(clazz, fieldOrMethod, returnType);
    }

    /**
     * 获取值
     *
     * @param target 目标object
     * @return 值
     */
    V get(T target);

}
