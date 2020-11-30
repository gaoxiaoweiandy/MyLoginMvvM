package com.example.myloginmvvm;

import android.app.Application;

/**
 * 可作为一个全局行的Context
 */
public class MyApplication extends Application {
    static public String protectorPicFilePath="";
    public static MyApplication instance;
    public static MyApplication getInstance()
    {
        if(instance == null)
        {
            instance = new MyApplication();
        }
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }
}
