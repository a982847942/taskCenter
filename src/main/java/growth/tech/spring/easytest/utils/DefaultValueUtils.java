package growth.tech.spring.easytest.utils;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 11:28
 */

import growth.tech.core.common.utils.AopUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

/**
 * 默认值工具类2
 *
 * @author lijiawei
 * @date 2024/08/28 14:01 星期三
 */
@SuppressWarnings("DuplicatedCode")
public class DefaultValueUtils {

    /**
     * 限制递归深度，避免无限递归
     */
    private static final int MAX_RECURSION_DEPTH = 10;

    /**
     * 获取类及其父类的默认值
     *
     * @param type 类型
     * @return 默认值
     */
    public static Object getDefaultValue(Type type) {
        return getDefaultValue(type, new HashSet<>(), 0);
    }

    /**
     * 获取类及其父类的默认值
     *
     * @param type           类型
     * @param visitedClasses 访问过的class
     * @param depth          当前递归深度
     * @return 默认值
     */
    @SneakyThrows
    private static Object getDefaultValue(Type type, Set<Type> visitedClasses, int depth) {
        if (depth > MAX_RECURSION_DEPTH) {
            return null; // 如果递归深度超出限制，返回null以防止无限递归
        }

        if (type instanceof Class<?>) {
            Class<?> clazz = (Class<?>) type;

            if (clazz.isPrimitive()) {
                return getPrimitiveDefault(clazz); // 处理原始类型
            } else if (isWrapperType(clazz)) {
                return getWrapperDefault(clazz); // 处理包装类型
            } else if (clazz == String.class) {
                return String.class.getName(); // 返回String的类名
            } else if (clazz.isEnum()) {
                // 处理枚举类型，考虑枚举中没有元素的情况
                Object[] enumConstants = clazz.getEnumConstants();
                return (enumConstants != null && enumConstants.length > 0) ? enumConstants[0] : null;
            } else if (clazz.isArray()) {
                // 处理数组类型
                Class<?> componentType = clazz.getComponentType();
                Object array = Array.newInstance(componentType, 1); // 创建长度为1的数组
                Array.set(array, 0, getDefaultValue(componentType, visitedClasses, depth + 1)); // 为数组填充默认值
                return array;
            } else if (visitedClasses.contains(clazz)) {
                return null; // 如果已经访问过该类，防止递归返回null
            } else if (Collection.class.isAssignableFrom(clazz)) {
                // 根据集合的具体类型返回不同的集合实现
                if (Set.class.isAssignableFrom(clazz)) {
                    return new HashSet<>(); // 返回HashSet作为Set的默认实现
                } else if (List.class.isAssignableFrom(clazz)) {
                    return new ArrayList<>(); // 返回ArrayList作为List的默认实现
                } else if (Queue.class.isAssignableFrom(clazz)) {
                    return new LinkedList<>(); // 返回LinkedList作为Queue的默认实现
                } else {
                    return new ArrayList<>(); // 默认返回ArrayList（也可以根据实际需要修改）
                }
            } else if (Map.class.isAssignableFrom(clazz)) {
                return new HashMap<>(); // 默认返回空Map
            } else if (isDateOrTimeType(clazz)) {
                return getDefaultDateOrTime(clazz); // 处理时间类型
            } else {
                try {
                    /* 下面获取构造方法 & newInstance可能会报错。异常没有吃掉让外部感知一下异常 */
                    visitedClasses.add(clazz); // 记录当前类，避免无限递归
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    Object instance = constructor.newInstance();
                    // 处理当前类及其所有父类的字段
                    fillFields(instance, clazz, visitedClasses, depth);
                    return instance;
                } finally {
                    visitedClasses.remove(clazz); // 清除类，允许之后的操作访问该类
                }
            }
        } else if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            Type rawType = paramType.getRawType();

            if (rawType instanceof Class<?> && Collection.class.isAssignableFrom((Class<?>) rawType)) {
                Type actualTypeArgument = paramType.getActualTypeArguments()[0]; // 获取Collection的泛型
                Collection<Object> collection = createCollectionInstance((Class<?>) rawType); // 根据类型创建相应的集合
                collection.add(getDefaultValue(actualTypeArgument, visitedClasses, depth + 1)); // 为Collection填充泛型默认值
                return collection;

            } else if (rawType instanceof Class<?> && Map.class.isAssignableFrom((Class<?>) rawType)) {
                Type keyType = paramType.getActualTypeArguments()[0]; // 获取Map键的泛型类型
                Type valueType = paramType.getActualTypeArguments()[1]; // 获取Map值的泛型类型
                Map<Object, Object> map = new HashMap<>();
                Object keyDefault = getDefaultValue(keyType, visitedClasses, depth + 1);
                Object valueDefault = getDefaultValue(valueType, visitedClasses, depth + 1);
                map.put(keyDefault, valueDefault); // 填充Map
                return map;
            }
        }

        return null;
    }

    /**
     * 获取Collection类型的默认值
     *
     * @param collectionType 集合类型
     * @return 集合类型的默认值
     */
    private static Collection<Object> createCollectionInstance(Class<?> collectionType) {
        if (Set.class.isAssignableFrom(collectionType)) {
            return new HashSet<>();
        } else if (List.class.isAssignableFrom(collectionType)) {
            return new ArrayList<>();
        } else if (Queue.class.isAssignableFrom(collectionType)) {
            return new LinkedList<>();
        } else {
            return new ArrayList<>(); // 默认返回ArrayList
        }
    }

    /**
     * 递归为instance对象及其继承的所有父类字段填充默认值
     *
     * @param instance       目标对象
     * @param clazz          对象类型
     * @param visitedClasses 访问过的class
     * @param depth          当前递归深度
     */
    private static void fillFields(Object instance, Class<?> clazz, Set<Type> visitedClasses, int depth) {
        if (clazz == null || clazz == Object.class) {
            return; // 如果到达Object类，停止递归
        }

        // 获取当前类的字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue; // 跳过静态字段
            }
            field.setAccessible(true);

            try {
                Type fieldType = field.getGenericType(); // 获取字段的泛型类型

                // 获取字段的默认值，考虑嵌套的泛型
                Object fieldValue = getDefaultValue(fieldType, visitedClasses, depth + 1);
                field.set(instance, fieldValue); // 设置字段的值
            } catch (Exception ignored) {
                // ignored。某个字段处理失败跳过。
            }
        }

        // 递归处理父类字段
        fillFields(instance, clazz.getSuperclass(), visitedClasses, depth);
    }

    /**
     * 获取基本数据类型的默认值
     *
     * @param clazz 类型
     * @return 默认值
     */
    private static Object getPrimitiveDefault(Class<?> clazz) {
        if (clazz == boolean.class) return false;
        if (clazz == char.class) return '\u0000';
        if (clazz == byte.class) return (byte) 0;
        if (clazz == short.class) return (short) 0;
        if (clazz == int.class) return 0;
        if (clazz == long.class) return 0L;
        if (clazz == float.class) return 0.0f;
        if (clazz == double.class) return 0.0;
        return null;
    }

    /**
     * 获取包装类型的默认值
     *
     * @param clazz 类型
     * @return 默认值
     */
    private static Object getWrapperDefault(Class<?> clazz) {
        if (clazz == Boolean.class) return false;
        if (clazz == Character.class) return '\u0000';
        if (clazz == Byte.class) return (byte) 0;
        if (clazz == Short.class) return (short) 0;
        if (clazz == Integer.class) return 0;
        if (clazz == Long.class) return 0L;
        if (clazz == Float.class) return 0.0f;
        if (clazz == Double.class) return 0.0;
        return null;
    }

    /**
     * 判断是否为包装类型
     *
     * @param clazz 类型
     * @return true代表是包装类型
     */
    private static boolean isWrapperType(Class<?> clazz) {
        return clazz == Boolean.class
                || clazz == Character.class
                || clazz == Byte.class
                || clazz == Short.class
                || clazz == Integer.class
                || clazz == Long.class
                || clazz == Float.class
                || clazz == Double.class;
    }

    /**
     * 判断是否为时间类型
     *
     * @param clazz 类型
     * @return true代表是时间类型
     */
    private static boolean isDateOrTimeType(Class<?> clazz) {
        return clazz == Date.class
                || clazz == LocalDate.class
                || clazz == LocalTime.class
                || clazz == LocalDateTime.class
                || clazz == Instant.class;
    }

    /**
     * 获取时间类型默认值（当前时间）
     *
     * @param clazz 类型
     * @return 时间类型默认值（当前时间）
     */
    private static Object getDefaultDateOrTime(Class<?> clazz) {
        if (clazz == Date.class) {
            return new Date(); // 返回当前Date
        } else if (clazz == LocalDate.class) {
            return LocalDate.now(); // 返回当前LocalDate
        } else if (clazz == LocalTime.class) {
            return LocalTime.now(); // 返回当前LocalTime
        } else if (clazz == LocalDateTime.class) {
            return LocalDateTime.now(); // 返回当前LocalDateTime
        } else if (clazz == Instant.class) {
            return Instant.now(); // 返回当前Instant
        }
        return null;
    }

}

