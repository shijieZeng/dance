package com.course.vo;

/**
 * Created by linxiao on 2017/12/21.
 */
public class ResResult {
    private int code;
    private String msg;
    private Object data;

    public ResResult() {
    }
    public ResResult(int code) {
        this.code = code;
    }
    public ResResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public ResResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResResult success() {
        return new ResResult(0, "success", "");
    }

}
