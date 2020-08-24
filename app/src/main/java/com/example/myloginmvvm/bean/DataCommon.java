package com.example.myloginmvvm.bean;

/**
 * 服务端返回的JSON数据的基类，该类包含了所有后台接口返回的公共字段
 */

public class DataCommon {
    int status;
    String msg;
    String time;
    Throwable throwable;

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

}
