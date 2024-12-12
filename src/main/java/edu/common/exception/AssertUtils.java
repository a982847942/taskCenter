package edu.common.exception;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/27 14:06
 */

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 断言工具类
 *
 * @author lijiawei
 * @date 2024/07/04 14:47 星期四
 */
public class AssertUtils {

    /**
     * 期望的正确值为true，如果实际为false，抛出异常<code>CommonException</code>
     *
     * @param expression 表达式
     * @param resultCode 错误代码
     * @throws CommonException 异常
     */
    public static void isTrue(boolean expression, ResultCode resultCode) throws CommonException {
        Objects.requireNonNull(resultCode, "result code is null");
        if (!expression) {
            throw new CommonException(resultCode);
        }
    }

    /**
     * 期望的正确值为true，如果实际为false，抛出异常<code>CommonException</code>
     *
     * @param expression 表达式
     * @param resultCode 错误代码
     * @param message    指定错误提示
     * @throws CommonException 异常
     */
    public static void isTrue(boolean expression, ResultCode resultCode, String message) throws CommonException {
        Objects.requireNonNull(resultCode, "result code is null");
        Objects.requireNonNull(message, "result message is null");
        if (!expression) {
            throw new CommonException(resultCode, message);
        }
    }

    /**
     * 期望的正确值为false，如果实际为true，抛出异常<code>CommonException</code>
     *
     * @param expression 表达式
     * @param resultCode 错误代码
     * @throws CommonException 异常
     */
    public static void isFalse(boolean expression, ResultCode resultCode) throws CommonException {
        isTrue(!expression, resultCode);
    }

    /**
     * 期望的正确值为false，如果实际为true，抛出异常<code>CommonException</code>
     *
     * @param expression    表达式
     * @param resultCode    错误代码
     * @param resultMessage 错误提示
     * @throws CommonException 异常
     */
    public static void isFalse(boolean expression, ResultCode resultCode, String resultMessage) throws CommonException {
        isTrue(!expression, resultCode, resultMessage);
    }

    /**
     * 期望对象为空
     *
     * @param object     对象
     * @param resultCode 结果码
     * @throws CommonException 异常
     */
    public static void isNull(Object object, ResultCode resultCode) throws CommonException {
        isTrue(object == null, resultCode);
    }

    /**
     * 期望对象为空
     *
     * @param object        对象
     * @param resultCode    结果码
     * @param resultMessage 错误提示
     * @throws CommonException 异常
     */
    public static void isNull(Object object, ResultCode resultCode, String resultMessage) throws CommonException {
        isTrue(object == null, resultCode, resultMessage);
    }
    /**
     * 期望对象为非空，如果检查的对象为<code>null</code>，抛出异常<code>CommonException</code>
     *
     * @param object     类
     * @param resultCode 错误码
     * @throws CommonException 异常
     */
    public static void isNotNull(Object object, ResultCode resultCode) throws CommonException {
        isTrue(object != null, resultCode);
    }

    /**
     * 期望对象为非空，如果检查的对象为<code>null</code>，抛出异常<code>CommonException</code>
     *
     * @param object        类
     * @param resultCode    错误码
     * @param resultMessage 错误提示
     * @throws CommonException 异常
     */
    public static void isNotNull(Object object, ResultCode resultCode, String resultMessage) throws CommonException {
        isTrue(object != null, resultCode, resultMessage);
    }

    /**
     * 期望字符串为非空，如果检查字符串是空白：<code>null</code>、空字符串""或只有空白字符，抛出异常ResultCodeEnum.PARAMETER_ILLEGAL
     *
     * @param text       待检查的字符串
     * @param resultCode 错误码
     * @throws CommonException 异常
     */
    public static void isNotBlank(String text, ResultCode resultCode) throws CommonException {
        /*
        commons-lang3包
         */
        isTrue(StringUtils.isNotBlank(text), resultCode);
    }

    /**
     * 期望字符串为非空，如果检查字符串是空白：<code>null</code>、空字符串""或只有空白字符，抛出异常ResultCodeEnum.PARAMETER_ILLEGAL
     *
     * @param text          待检查的字符串
     * @param resultCode    错误码
     * @param resultMessage 错误提示
     * @throws CommonException 异常
     */
    public static void isNotBlank(String text, ResultCode resultCode, String resultMessage) throws CommonException {
        isTrue(StringUtils.isNotBlank(text), resultCode, resultMessage);
    }

    /**
     * 期望字符串为空，如果检查字符串非空抛出异常ResultCodeEnum.PARAMETER_ILLEGAL
     *
     * @param text       待检查的字符串
     * @param resultCode 错误码
     * @throws CommonException 异常
     */
    public static void isBlank(String text, ResultCode resultCode) throws CommonException {
        isTrue(StringUtils.isBlank(text), resultCode);
    }

    /**
     * 期望字符串为空，如果检查字符串非空，抛出异常<code>CommonException</code>
     *
     * @param text          待检查的字符串
     * @param resultCode    错误码
     * @param resultMessage 错误提示
     * @throws CommonException 异常
     */
    public static void isBlank(String text, ResultCode resultCode, String resultMessage) throws CommonException {
        isTrue(StringUtils.isBlank(text), resultCode, resultMessage);
    }

    /**
     * 期望数组为非空，如果检查数组对象是否为null或者空数据，抛出异常<code>CommonException</code>
     *
     * @param array      数组对象
     * @param resultCode 错误码
     * @throws CommonException 异常
     */
    public static <T> void isNotEmpty(T[] array, ResultCode resultCode) throws CommonException {
        isTrue(array != null && array.length > 0, resultCode);
    }

    /**
     * 期望数组为非空，如果检查数组对象是否为null或者空数据，抛出异常<code>CommonException</code>
     *
     * @param array         数组对象
     * @param resultCode    错误码
     * @param resultMessage 错误提示
     * @throws CommonException 异常
     */
    public static <T> void isNotEmpty(T[] array, ResultCode resultCode, String resultMessage) throws CommonException {
        isTrue(array != null && array.length > 0, resultCode, resultMessage);
    }

    /**
     * 期望集合对象为非空，如果检查集合对象是否为null或者空数据，抛出异常<code>CommonException</code>
     *
     * @param collection 集合对象
     * @param resultCode 异常代码
     * @throws CommonException 异常
     */
    public static void isNotEmpty(Collection<?> collection, ResultCode resultCode) throws CommonException {
        isTrue(collection != null && !collection.isEmpty(), resultCode);
    }
    /**
     * 期望集合对象为非空，如果检查集合对象是否为null或者空数据，抛出异常<code>CommonException</code>
     *
     * @param collection    集合对象
     * @param resultCode    异常代码
     * @param resultMessage 错误提示
     * @throws CommonException 异常
     */
    public static void isNotEmpty(Collection<?> collection, ResultCode resultCode, String resultMessage)
            throws CommonException {
        isTrue(collection != null && !collection.isEmpty(), resultCode, resultMessage);
    }

    /**
     * 期望集合对象为非空，如果检查集合对象是否为null或者空数据，抛出异常<code>CommonException</code>
     *
     * @param map        map对象
     * @param resultCode 异常代码
     * @throws CommonException 异常
     */
    public static void isNotEmpty(Map<?, ?> map, ResultCode resultCode) throws CommonException {
        isTrue(map != null && !map.isEmpty(), resultCode);
    }

    /**
     * 期望集合对象为非空，如果检查集合对象是否为null或者空数据，抛出异常<code>CommonException</code>
     *
     * @param map           map对象
     * @param resultCode    异常代码
     * @param resultMessage 错误提示
     * @throws CommonException 异常
     */
    public static void isNotEmpty(Map<?, ?> map, ResultCode resultCode, String resultMessage) throws CommonException {
        isTrue(map != null && !map.isEmpty(), resultCode, resultMessage);
    }

    /**
     * 期望集合对象相等，如果检查集合对象不相等，抛出异常<code>CommonException</code>
     *
     * @param a          a对象
     * @param b          b对象
     * @param resultCode 异常代码
     * @throws CommonException 异常
     */
    public static <T> void isEquals(T a, T b, ResultCode resultCode) throws CommonException {
        isTrue(Objects.equals(a, b), resultCode);
    }

    /**
     * 期望集合对象相等，如果检查集合对象不相等，抛出异常<code>CommonException</code>
     *
     * @param a             a对象
     * @param b             b对象
     * @param resultCode    异常代码
     * @param resultMessage 错误提示
     * @throws CommonException 异常
     */
    public static <T> void isEquals(T a, T b, ResultCode resultCode, String resultMessage) throws CommonException {
        isTrue(Objects.equals(a, b), resultCode, resultMessage);
    }

    /**
     * 期望集合对象相等，如果检查集合对象不相等，抛出异常<code>CommonException</code>
     *
     * @param a          a对象
     * @param b          b对象
     * @param resultCode 异常代码
     * @throws CommonException 异常
     */
    public static <T> void isNotEquals(T a, T b, ResultCode resultCode) throws CommonException {
        isTrue(ObjectUtils.notEqual(a, b), resultCode);
    }

    /**
     * 期望集合对象相等，如果检查集合对象不相等，抛出异常<code>CommonException</code>
     *
     * @param a             a对象
     * @param b             b对象
     * @param resultCode    异常代码
     * @param resultMessage 错误提示
     * @throws CommonException 异常
     */
    public static <T> void isNotEquals(T a, T b, ResultCode resultCode, String resultMessage) throws CommonException {
        isTrue(ObjectUtils.notEqual(a, b), resultCode, resultMessage);
    }

}
