package com.example.myloginmvvm.bean;

/**
 * 登录接口返回的JSON数据结构对应的实体类
 */
public class JsonLogin extends DataCommon{
    JsonLoginData data;

    public JsonLoginData getData() {
        return data;
    }

    public void setData(JsonLoginData data) {
        this.data = data;
    }

    @Override
    public String toString() {
       return "status="+status+",msg="+msg+",time="+time+",throwable="+throwable;
    }
}
