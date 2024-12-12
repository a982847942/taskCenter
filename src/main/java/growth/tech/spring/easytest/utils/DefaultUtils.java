package growth.tech.spring.easytest.utils;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 11:28
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.temporal.Temporal;
import java.util.*;

/**
 * 默认值工具类
 */
@Deprecated
public class DefaultUtils {

    /**
     * 获取默认值
     *
     * @param clazz   类型
     * @param maxDeep 最大递归深度
     * @return 默认值
     */
    public static Object getDefaultValue(Class<?> clazz, int maxDeep) {
        return getDefaultValue(clazz, maxDeep, 0, new LinkedHashMap<>());
    }

    /**
     * 获取默认值
     *
     * @param clazz    类型
     * @param maxDeep  最大递归深度
     * @param currDeep 当前递归深度
     * @param cache    默认值缓存
     * @return 默认值
     */
    private static Object getDefaultValue(Class<?> clazz, int maxDeep, int currDeep,
                                          LinkedHashMap<String, Object> cache) {
        if (clazz == null) {
            return null;
        }
        // 递归退出条件
        if (currDeep > maxDeep) {
            return clazz.getName();
        }
        // 兜底
        if (clazz.isAssignableFrom(Object.class)) {
            return cache;
        }

        // 递归深度++
        currDeep++;

        // 处理基本数据类型
        if (clazz == boolean.class || clazz == Boolean.class) {
            return false;
        } else if (clazz == char.class || clazz == Character.class) {
            return '\u0000';
        } else if (clazz == byte.class || clazz == Byte.class) {
            return (byte) 0;
        } else if (clazz == short.class || clazz == Short.class) {
            return (short) 0;
        } else if (clazz == int.class || clazz == Integer.class) {
            return 0;
        } else if (clazz == long.class || clazz == Long.class) {
            return 0L;
        } else if (clazz == float.class || clazz == Float.class) {
            return 0.0f;
        } else if (clazz == double.class || clazz == Double.class) {
            return 0.0d;
        } else if (clazz.isArray() || Collection.class.isAssignableFrom(clazz)) {
            // 处理数组类型
            return Lists.newArrayList();
        } else if (Map.class.isAssignableFrom(clazz)) {
            return Maps.newHashMap();
        } else if (Date.class.isAssignableFrom(clazz) || Temporal.class.isAssignableFrom(clazz)) {
            return new Date();
        } else if (clazz == String.class) {
            // 特殊处理 String 类型
            return clazz.getName();
        } else if (clazz.isEnum()) {
            // 枚举默认取第一个枚举实例值
            return clazz.getEnumConstants() == null || clazz.getEnumConstants().length == 0
                    ? clazz.getName()
                    : ((Enum<?>) clazz.getEnumConstants()[0]).name();
        } else {
            // 对象类型，返回一个map，里面的字段递归处理
            try {
                Field[] fields = Arrays.stream(clazz.getDeclaredFields())
                        // 跳过static的字段
                        .filter(f -> !Modifier.isStatic(f.getModifiers()))
                        .toArray(Field[]::new);
                for (Field field : fields) {
                    Class<?> fieldType = field.getType();
                    // cache(子类)有这个字段的话跳过。其实如果父子类字段相同而类型不同会编译报错。
                    if (cache.containsKey(field.getName())) {
                        continue;
                    }
                    cache.put(field.getName(), getDefaultValue(fieldType, maxDeep, currDeep, new LinkedHashMap<>()));
                }

                // 递归获取父类的字段
                if (!clazz.isAssignableFrom(Object.class)) {
                    Class<?> superclass = clazz.getSuperclass();
                    if (superclass != null) {
                        // 父类字段递归深度从0开始
                        return getDefaultValue(superclass, maxDeep, 0, cache);
                    }
                }
                return cache;
            } catch (Exception ignored) {
                return null;
            }
        }
    }

}
