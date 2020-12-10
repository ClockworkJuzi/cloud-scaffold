package pers.regulus.cloud.common.exception;

import pers.regulus.cloud.common.enums.ResultEnum;

/**
 * BaseException
 *
 * @author Regulus
 */
public class BaseException extends RuntimeException {

    private ResultEnum result;

    public BaseException() {

    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(ResultEnum result) {
        this.result = result;
    }

    public BaseException(ResultEnum result, String message) {
        super(message);
        this.result = result;
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

    public ResultEnum getResult() {
        return result;
    }

    public void setResult(ResultEnum result) {
        this.result = result;
    }
}
