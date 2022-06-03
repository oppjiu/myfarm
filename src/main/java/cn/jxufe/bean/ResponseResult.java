package cn.jxufe.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @create: 2022-05-25 20:54
 * @author: lwz
 * @description:
 **/
public class ResponseResult<T> implements Serializable {
    private Integer code;//响应码
    private String message;//响应信息

    private boolean isError;
    private T data;//数据

    public ResponseResult() {
    }

    public ResponseResult(ResponseCode responseCode) {
        this.code = responseCode.code();
        this.message = responseCode.message();
    }

    public ResponseResult(ResponseCode responseCode, T data) {
        this.code = responseCode.code();
        this.message = responseCode.message();
        this.data = data;
    }

    public ResponseResult(ResponseCode responseCode, boolean isError) {
        this.code = responseCode.code();
        this.message = responseCode.message();
        this.isError = isError;
    }

    public ResponseResult(ResponseCode responseCode, boolean isError, T data) {
        this.code = responseCode.code();
        this.message = responseCode.message();
        this.data = data;
        this.isError = isError;
    }

    public static ResponseResult<?> success() {
        return new ResponseResult<>(ResponseCode.SUCCESS);
    }

    public static ResponseResult<?> success(Object data) {
        return new ResponseResult<>(ResponseCode.SUCCESS, data);
    }

    public static ResponseResult<?> failure(ResponseCode responseCode) {
        return new ResponseResult<>(responseCode);
    }

    public static ResponseResult<?> failure(ResponseCode responseCode, Object data) {
        ResponseResult<Object> result = new ResponseResult<>(responseCode, data);
        result.setData(data);
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
