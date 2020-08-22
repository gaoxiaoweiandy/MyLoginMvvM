package com.example.myloginmvvm.bean;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


public class User {
    //现在是以userPhone为账号名登录的
    private String userPhone;
    private String password;
    private String userId;
    private String nickName;
    private Context mContext;
   static public String userToken;
    private String headPicFilePath;
    private String protectorPicFilePath;

    public String getProtectorPicFilePath() {
        return protectorPicFilePath;
    }

    public void setProtectorPicFilePath(String protectorPicFilePath) {
        this.protectorPicFilePath = protectorPicFilePath;
    }

    public String getHeadPicFilePath() {
        return headPicFilePath;
    }

    public void setHeadPicFilePath(String headPicFilePath) {
        this.headPicFilePath = headPicFilePath;
    }

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

    public User()
    {
        ;
    }

    public User(Context context)
    {
        this.mContext = context;
        SharedPreferences sp = context.getSharedPreferences("login",Context.MODE_PRIVATE);
        this.userId = sp.getString("userId", "");
        this.nickName = sp.getString("nickName","");
        this.password = sp.getString("password","");
        this.userPhone = sp.getString("userPhone","");
        this.userToken = sp.getString("userToken","");
        this.headPicFilePath = sp.getString("headPicPath","");
        this.protectorPicFilePath = sp.getString("protectorPicFilePath","");
    }

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

    /**
     *  用户退出登录，清除所有用户信息
     * @param context
     *
     */
    public void userLogOut(Context context)
    {
        SharedPreferences loginPreference = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = loginPreference.edit();
        edit.putString("userId", "");
        edit.putString("nickName","");
        //这里存储的是未加密的源码，以后登录时候要给密码加密。先这样测试几天，然后再存储成加密后的密码，以后登录就不需要再加密了
        edit.putString("password","");
        //gxw-edit.putString("userPhone","");
        edit.putString("userToken","");
        edit.commit();
    }

    /**
     * 判断是否已登录（记住登录）
     * @return
     */
    public boolean isLogin() {

        if(!TextUtils.isEmpty(userId))
        {
            return true;
        }
        return false;
    }

}
