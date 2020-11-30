package com.example.myloginmvvm.bean;

import android.os.Environment;

/**
 * 常量
 */
public class MyConstant {
    public static final String BUILD_TYPE = "debug";
    public final static String ROOT_DEBUG = "http://www.ayksos.com/";
    public final static String ROOT_RELEASE = "http://www.ayksos.com/";
    public static final String PIC_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TakePhotoPic";
}

