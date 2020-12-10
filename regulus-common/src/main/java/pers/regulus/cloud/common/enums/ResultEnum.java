package pers.regulus.cloud.common.enums;

/**
 * 操作结果编码枚举类
 *
 * @author Regulus
 */
public enum ResultEnum {

    /* 成功状态码 */
    SUCCESS(0, "操作成功。"),

    /* 失败状态码 */
    FAILED(99, "操作失败。"),

    /* 参数错误：1001~1999 */
    PARAM_IS_INVALID(1001, "参数无效。"),
    PARAM_IS_BLANK(1002, "参数为空。"),
    PARAM_TYPE_BIND_ERROR(1003, "参数类型错误。"),
    PARAM_NOT_COMPLETE(1004, "参数缺失。");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
