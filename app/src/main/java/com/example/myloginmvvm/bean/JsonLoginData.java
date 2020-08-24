package com.example.myloginmvvm.bean;

/**
 * 登录接口返回的JsonLogin实体类中的 data数据
 */
public class JsonLoginData {
    public String userId;
    public String name;
    public String mobile;
    public String token;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
