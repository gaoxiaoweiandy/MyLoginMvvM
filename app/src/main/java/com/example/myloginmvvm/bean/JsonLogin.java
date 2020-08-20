package com.example.myloginmvvm.bean;

import androidx.annotation.NonNull;

public class JsonLogin extends DataCommon{
    @NonNull
    @Override
    public String toString() {
       return "status="+status+",msg="+msg+",time="+time+",throwable="+throwable;
    }
}
