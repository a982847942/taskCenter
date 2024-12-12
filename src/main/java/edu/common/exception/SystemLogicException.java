package edu.common.exception;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/27 14:01
 */
public class SystemLogicException extends CommonException{
    private static final long serialVersionUID = -1L;
    public SystemLogicException(String message) {
        super(message);
    }

    public SystemLogicException(int code, String message) {
        super(code, message);
    }

    public SystemLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemLogicException(ResultCode resultCode) {
        super(resultCode);
    }

    public SystemLogicException(ResultCode resultCode, String message) {
        super(resultCode, message);
    }

    public SystemLogicException(ResultCode resultCode, String message, Throwable cause) {
        super(resultCode, message, cause);
    }

    public SystemLogicException(ResultCode resultCode, String message, String codeDescription, Throwable cause) {
        super(resultCode, message, codeDescription, cause);
    }

    public SystemLogicException(int code, String message, boolean retryable, String codeDescription, Throwable cause) {
        super(code, message, retryable, codeDescription, cause);
    }
}
