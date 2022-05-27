package cn.jxufe.bean;

/**
 * @create: 2022-05-25 20:56
 * @author: lwz
 * @description:
 **/
public enum ResponseCode {
    /**
     * 全局状态码
     */
    SUCCESS(10, "操作成功！"),
    ERROR(0, "操作失败！"),
    INVALID_PARAM(10003, "非法参数！"),
    UNAUTHENTICATED(10001, "此操作需要登陆系统！"),
    UNAUTHORISE(10002, "权限不⾜，⽆权操作！"),
    SERVER_ERROR(99999, "抱歉，系统繁忙，请稍后重试！");

    final int code;//状态码
    final String message;//文字信息

    /**
     * 构造方法
     *
     * @param code
     * @param message
     */
    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取文字信息
     *
     * @param name
     * @return
     */
    public static String getMessage(String name) {
        for (ResponseCode item : ResponseCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    /**
     * 获取状态码
     *
     * @param name
     * @return
     */
    public static Integer getCode(String name) {
        for (ResponseCode item : ResponseCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
