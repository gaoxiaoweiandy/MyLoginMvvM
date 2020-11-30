package com.example.myloginmvvm.utils;
import android.text.TextUtils;
import android.util.Log;

/***
 * 控制日志是否打印
 */
public final class MyLogCat {
    public static boolean isPrint = true;
    private static String defaultTag = "fallingAlarm";

    public static void d(String tag,String msg) {
        if (isPrint && msg != null)
        {
            if(TextUtils.isEmpty(tag))
            {
                tag = defaultTag;
            }
            Log.d(tag, msg);
        }
    }

    public static void i(String tag,String msg) {
        if (isPrint && msg != null)
        {
            if(TextUtils.isEmpty(tag))
            {
                tag = defaultTag;
            }
            Log.i(tag, msg);
        }

    }

    public static void w(String tag,String msg) {
        if (isPrint && msg != null)
        {
            if(TextUtils.isEmpty(tag))
            {
                tag = defaultTag;
            }
            Log.w(tag, msg);
        }
    }

    public static void e(String tag,String msg) {
        if (isPrint && msg != null)
        {
            if(TextUtils.isEmpty(tag))
            {
                tag = defaultTag;
            }
            Log.e(tag, msg);
        }
    }

}
