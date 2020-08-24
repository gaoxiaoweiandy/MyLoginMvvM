package com.example.myloginmvvm.bean;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * 本地User类，用于保存登录接口返回的用户信息，尤其是token.
 */
public class User {
    //现在是以userPhone为账号名登录的
    private String userPhone;
    private String password;
    private String userId;
    private String nickName;
    private Context mContext;
    static public String userToken;



    /**
     * 构造函数，无需读取本地存储
     */
    public User()
    {
        ;
    }

    /**
     * 构造函数，同时读取本地存储
     * @param context
     */
    public User(Context context)
    {
        this.mContext = context;
        SharedPreferences sp = mContext.getSharedPreferences("login",Context.MODE_PRIVATE);
        this.userId = sp.getString("userId", "");
        this.nickName = sp.getString("nickName","");
        this.password = sp.getString("password","");
        this.userPhone = sp.getString("userPhone","");
        this.userToken = sp.getString("userToken","");
    }
    /**
     * 保存用户信息
     * @param context
     * @param user：登录接口返回的用户信息
     */
    public void saveUserInfo(Context context ,ContentValues user)
    {
        if(user != null)
        {
            SharedPreferences loginPreference = context.getSharedPreferences("login", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = loginPreference.edit();
            String userId =  user.getAsString("userId");
            String nickName =  user.getAsString("nickName");
            String password =  user.getAsString("password");
            String userPhone =  user.getAsString("userPhone");
            String userToken =  user.getAsString("userToken");
            String headPicFilePath =  user.getAsString("headPicPath");
            String protectorPicFilePath =  user.getAsString("protectorPicFilePath");

            if(!TextUtils.isEmpty(userId))
            {
                edit.putString("userId", userId);
            }

            if(!TextUtils.isEmpty(nickName))
            {
                edit.putString("nickName", nickName);
            }

            if(!TextUtils.isEmpty(password))
            {
                edit.putString("password", password);
            }
            if(!TextUtils.isEmpty(userPhone))
            {
                edit.putString("userPhone", userPhone);
            }
            if(!TextUtils.isEmpty(userToken))
            {
                edit.putString("userToken", userToken);
            }
            if(!TextUtils.isEmpty(headPicFilePath))
            {
                edit.putString("headPicPath", headPicFilePath);
            }

            if(!TextUtils.isEmpty(protectorPicFilePath))
            {
                edit.putString("protectorPicFilePath", protectorPicFilePath);
            }
            edit.commit();
        }
    }

    //getter setter函数
    public String getUserToken() {
        return userToken;
    }
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}
