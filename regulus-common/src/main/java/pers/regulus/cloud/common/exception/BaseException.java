package pers.regulus.cloud.common.exception;

/**
 * BaseException
 * @author Regulus
 */
public class BaseException extends RuntimeException{

    private int status = 200;

    public BaseException() {

    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
