package edu.common.exception;

import lombok.Getter;

/**
 * 通用异常
 *
 * @author brain
 * @version 1.0
 * @date 2024/10/27 13:24
 */
@Getter
public class CommonException extends RuntimeException {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -1L;

    /**
     * 结果码
     * 该值会透传到RPC层的Result code上
     */
    private final int code;

    /**
     * 结果信息
     * 该值会透传到RPC层的Result message上
     */
    private final String message;

    /**
     * 是否可以重试
     */
    private final boolean retryable;

    /**
     * 错误码描述
     * 该值用于监控打点使用，不会暴露给前端
     */
    private final String codeDescription;

    /**
     * 通过错误码枚举构造
     * @param message
     */
    public CommonException(String message){
        this(CommonResultCodeEnum.SYSTEM_ERROR, message, CommonResultCodeEnum.SYSTEM_ERROR.getCodeDescription(), null);
    }
    public CommonException(int code, String message){
        this(code, message, CommonResultCodeEnum.SYSTEM_ERROR.isRetryable(), CommonResultCodeEnum.SYSTEM_ERROR.getCodeDescription(), null);
    }
    public CommonException(String message, Throwable cause){
        this(CommonResultCodeEnum.SYSTEM_ERROR, message, CommonResultCodeEnum.SYSTEM_ERROR.getCodeDescription(), cause);
    }

    public CommonException(ResultCode resultCode) {
        this(resultCode, resultCode.getMessage(), resultCode.getCodeDescription(), null);
    }
    public CommonException(ResultCode resultCode, String message) {
        this(resultCode, message, resultCode.getCodeDescription(), null);
    }
    public CommonException(ResultCode resultCode, String message, Throwable cause) {
        this(resultCode.getCode(), message, resultCode.isRetryable(), resultCode.getCodeDescription(), cause);
    }
    public CommonException(ResultCode resultCode, String message, String codeDescription, Throwable cause) {
        this(resultCode.getCode(), message, resultCode.isRetryable(), codeDescription, cause);
    }

    /**
     * 通过错误码和异常构造
     * @param code 结果码
     * @param message 自定义错误文案
     * @param retryable 是否可重试
     * @param codeDescription 错误码描述
     * @param cause 异常
     */
    public CommonException(int code, String message, boolean retryable, String codeDescription, Throwable cause) {
        super(code + "," + "codeDescription" + "," + message, cause);
        this.code = code;
        this.message = message;
        this.retryable = retryable;
        this.codeDescription = codeDescription;
    }

}
