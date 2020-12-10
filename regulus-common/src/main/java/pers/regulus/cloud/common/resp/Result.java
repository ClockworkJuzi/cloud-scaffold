package pers.regulus.cloud.common.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pers.regulus.cloud.common.enums.ResultEnum;

import java.io.Serializable;

/**
 * 操作返回基类
 *
 * @param <T>
 * @author Regulus
 */
@Data
@ToString
@EqualsAndHashCode
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 5036848920676023309L;

    private int code = 0;
    private String message;
    private T data;

    public Result() {
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<T>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), data);
    }

}
