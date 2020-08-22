package com.example.myloginmvvm.bean;

import androidx.annotation.NonNull;

public class JsonLogin extends DataCommon{
    LoginData data;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    @Override
    public String toString() {
       return "status="+status+",msg="+msg+",time="+time+",throwable="+throwable;
    }
}
