package com.example.myloginmvvm.bean;

/**
 * 服务端返回的JSON数据的基类，该类包含了所有后台接口返回的公共字段
 * 其中T泛型，表示服务器返回的data业务数据类型，在JSON中data是key，而value的类型和数据结构是多变的，
 * 因此为了重用和简少代码，这里对data的类型使用了泛型T（还有一个objectbean，这个是由于服务端接口不规范
 * ，按理说key统一为data）。
 */

public class JsonDataCommon<T> {
    int status;    //1成功，0业务失败，-1网络等异常
    String msg;    //错误消息
    String time;   //接口返回时间
    T  data;       //返回的业务数据
    T  objectbean; //这个是由于服务端接口不规范 ，按理说key统一为data

    Throwable throwable; //异常信息

    /**
     * 判断接口业务是否成功，并非请求网络是否成功
     * @return
     */
    public boolean isSuccess()
    {
        if(status == 1)
        {
            return true;
        }
        return false;
    }

    /**
     * 获取服务器接口JSON中的核心数据，在JSON中的表现形式是key为“data”或 “objectbean”时，对应的value。
     * @return
     */
    //JSON中key为objectbean的核心数据
    public T getObjectbean() {
        return objectbean;
    }

    public void setObjectbean(T objectbean) {
        this.objectbean = objectbean;
    }

    //JSON中key为data的核心数据
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 获取请求接口失败时的异常信息，如网络异常等。
     * @return
     */
    public Throwable getThrowable() {
        return throwable;
    }
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    /**
     * 获取接口业务 成功与否
     * @return
     */
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }


    /**
     * 获取接口业务 失败的原因(这里指业务原因：如账号没有注册)
     * @return
     */
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }


    /**
     * 接口返回的时间，这个用于记录访问接口的历史日志时间，业务逻辑中暂时没有用。
     * @return
     */
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

}
