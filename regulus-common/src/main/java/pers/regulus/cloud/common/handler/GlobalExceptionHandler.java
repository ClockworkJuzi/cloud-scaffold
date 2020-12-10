package pers.regulus.cloud.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.regulus.cloud.common.exception.BaseException;
import pers.regulus.cloud.common.resp.Result;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理类
 *
 * @author Regulus
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public Result<?> baseExceptionHandler(HttpServletResponse response, BaseException exc) {
        log.error(exc.getMessage(), exc);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new Result(exc.getResult().getCode(), exc.getResult().getMsg());
    }

    @ExceptionHandler(Exception.class)
    public Result<?> exceptionHandler(HttpServletResponse response, Exception exc) {
        log.error(exc.getMessage(), exc);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new Result(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exc.getMessage());
    }

}
