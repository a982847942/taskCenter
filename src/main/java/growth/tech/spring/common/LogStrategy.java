package growth.tech.spring.common;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:23
 */
/**
 * 切面打印日志策略
 */
public enum LogStrategy {

    /*
        切面打出来的日志形如：
        - INFO日志：
            INFO [classSimpleName][methodName][success][costTime] ([入参1][入参2]>>>>>>>>>>[返回值])

        - CommonException（如果是CommonException会把code码打出来）：
            ERROR [classSimpleName][methodName][CommonException(code)][costTime] ([入参1][入参2]>>>>>>>>>>[null]) 异常堆栈
        - 其他异常（digest只打印异常的classSimpleName，异常堆栈会在此摘要日志后打印）：
            ERROR [classSimpleName][methodName][IllegalArgumentException][costTime] ([入参1][入参2]>>>>>>>>>>[null]) 异常堆栈
     */

    /** 不打印任何日志 */
    @Deprecated
    NONE,

    /** 打印完整日志，无论调用成功/失败，都会打印一条。成功打印info，报错打印error */
    ALL,

    /** 只有报错才打印一条error日志 */
    ONLY_ERROR,

}
