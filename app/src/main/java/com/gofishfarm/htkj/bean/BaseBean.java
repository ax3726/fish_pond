package com.gofishfarm.htkj.bean;

/**
 * MrLiu253@163.com
 *
 * @time 2018/7/30
 */

public class BaseBean <T>{

    private int code;
    private String message;

    /**
     * 服务器返回的数据
     */
    public T data;


    public BaseBean(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code ;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
