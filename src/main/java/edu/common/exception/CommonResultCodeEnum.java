package edu.common.exception;

/**
 * 常用结果码枚举
 * @author brain
 * @version 1.0
 * @date 2024/10/27 13:45
 */
public enum CommonResultCodeEnum implements ResultCode{
    SUCCESS(0, "成功", false),
    SYSTEM_ERROR(10000, "系统异常", true),
    PARAMETER_ILLEGAL(10001, "参数错误", false),
    INTEGRATION_ERROR(10002, "下游异常", true),
    STORE_ERROR(10003, "存储异常", true),
    CONCURRENT_UPDATE(10004, "并发更新", true),
    BILL_DUPLICATE(10005, "流水重复", false),
    CONFIG_ERROR(10006, "配置异常", true),
    CONFIG_NOT_EXIST(10007, "配置不存在", true),
    LOCK_FAIL(100008, "加锁失败", true),
    ;

    private final int code;
    private final String message;
    private final boolean isRetryable;

    CommonResultCodeEnum(int code, String message, boolean isRetryable) {
        this.code = code;
        this.message = message;
        this.isRetryable = isRetryable;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public boolean isRetryable() {
        return this.isRetryable;
    }

    @Override
    public String getCodeDescription() {
        return this.name();
    }
}
