package edu.common.exception;

/**
 * 统一下游异常，下游的异常(如DB、Redis、下游服务)都统一抛出这个异常类
 * @author brain
 * @version 1.0
 * @date 2024/10/27 13:56
 */
public class DownStreamException extends CommonException{
    private static final long serialVersionUID = -1L;
    public DownStreamException(String message) {
        super(message);
    }

    public DownStreamException(int code, String message) {
        super(code, message);
    }

    public DownStreamException(String message, Throwable cause) {
        super(message, cause);
    }

    public DownStreamException(ResultCode resultCode) {
        super(resultCode);
    }

    public DownStreamException(ResultCode resultCode, String message) {
        super(resultCode, message);
    }

    public DownStreamException(ResultCode resultCode, String message, Throwable cause) {
        super(resultCode, message, cause);
    }

    public DownStreamException(ResultCode resultCode, String message, String codeDescription, Throwable cause) {
        super(resultCode, message, codeDescription, cause);
    }

    public DownStreamException(int code, String message, boolean retryable, String codeDescription, Throwable cause) {
        super(code, message, retryable, codeDescription, cause);
    }
}
