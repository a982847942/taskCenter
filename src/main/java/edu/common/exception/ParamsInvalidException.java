package edu.common.exception;

/**
 * 业务系统的参数异常，为了和java，=.lang.illegalArgumentException做区分
 * @author brain
 * @version 1.0
 * @date 2024/10/27 13:59
 */
public class ParamsInvalidException extends CommonException{
    private static final long serialVersionUID = -1L;
    public ParamsInvalidException(String message) {
        super(message);
    }

    public ParamsInvalidException(int code, String message) {
        super(code, message);
    }

    public ParamsInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamsInvalidException(ResultCode resultCode) {
        super(resultCode);
    }

    public ParamsInvalidException(ResultCode resultCode, String message) {
        super(resultCode, message);
    }

    public ParamsInvalidException(ResultCode resultCode, String message, Throwable cause) {
        super(resultCode, message, cause);
    }

    public ParamsInvalidException(ResultCode resultCode, String message, String codeDescription, Throwable cause) {
        super(resultCode, message, codeDescription, cause);
    }

    public ParamsInvalidException(int code, String message, boolean retryable, String codeDescription, Throwable cause) {
        super(code, message, retryable, codeDescription, cause);
    }
}
