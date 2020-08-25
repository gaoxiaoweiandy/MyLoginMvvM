package com.example.myloginmvvm.bean;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.myloginmvvm.BR;

public class TwoDirectionBinding extends BaseObservable {
    private User user;
    String TAG = "TwoDirectionBinding";

    public TwoDirectionBinding()
    {
        user = new User();
        user.setUserPhone("18392086025");
        user.setPassword("csdn3412");
    }

    /**
     * 双向绑定 用户名 之从变量到UI
     * @return
     */
    @Bindable
    public String getUserName()
    {
        return user.getUserPhone();
    }

    /**
     * 双向绑定 用户名 之从UI到变量
     * @return
     */
    public void setUserName(String userName)
    {
        if(userName != null && !userName.equals(user.getUserPhone()))
        {
            user.setUserPhone(userName);
            notifyPropertyChanged(BR.userName);
            Log.i(TAG,"用户名双向绑定，从UI到变量,userName="+user.getUserPhone());
        }

    }

    /**
     * 双向绑定 密码 之从变量到UI
     * @return
     */
    @Bindable
    public String getPassword()
    {
        return user.getPassword();
    }

    /**
     * 双向绑定 密码 之从UI到变量
     * @return
     */
    public void setPassword(String password)
    {
        if(password != null && !password.equals(user.getPassword()))
        {
            user.setPassword(password);
            notifyPropertyChanged(BR.password);
            Log.i(TAG,"密码双向绑定，从UI到变量,password="+user.getPassword());
        }

    }
}
