package com.example.myloginmvvm.bean;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.myloginmvvm.BR;

/**
 * 双向绑定:
 *
 * 方向1：从UI到变量
 * EditText的内容被用户手动修改后，会自动调用setUserName（userName）\setPassword(password)，
 * 将ditText的内容传递过来，用于修改本地javaBean对象中的成员变量。
 *
 * 方向2：从变量到UI
 * getUserName和getPassword方法上添加注解：@Bindable
 *
 * 最后需要在layout布局文件里的EditText.text属性 赋值为如下表达式：
 * @={twoDirectionBinding.password}
 * @={twoDirectionBinding.userName}
 *
 */
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
        String userName = user.getUserPhone();
        Log.i(TAG,"从变量到UI,userName="+userName);
        return userName;
    }

    /**
     * 双向绑定 用户名 之从UI到变量
     * @return
     */
    public void setUserName(String userName)
    {
            user.setUserPhone(userName);
            notifyPropertyChanged(BR.userName);
            Log.i(TAG,"用户名双向绑定，从UI到变量,userName="+user.getUserPhone());
    }

    /**
     * 双向绑定 密码 之从变量到UI
     * @return
     */
    @Bindable
    public String getPassword()
    {
        String password = user.getPassword();
        Log.i(TAG,"从变量到UI,password="+password);
        return password;
    }

    /**
     * 双向绑定 密码 之从UI到变量
     * @return
     */
    public void setPassword(String password)
    {
            user.setPassword(password);
            notifyPropertyChanged(BR.password);
            Log.i(TAG,"密码双向绑定，从UI到变量,password="+user.getPassword());
    }
}
