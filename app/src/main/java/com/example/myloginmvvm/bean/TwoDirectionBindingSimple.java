package com.example.myloginmvvm.bean;
import android.util.Log;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;


/**
 * 双向绑定的简化,TwoDirectionBinding的简化版，使用ObservableField<T>包装数据对象
 */
public class TwoDirectionBindingSimple{
    ObservableField<User> userObservableField;
    String TAG = "TwoDirectionBinding";

    public TwoDirectionBindingSimple()
    {
        User user = new User();
        user.setUserPhone("18392086025");
        user.setPassword("csdn3412");
        userObservableField = new ObservableField<>();
        userObservableField.set(user);
    }

    /**
     * 双向绑定 用户名 之从变量到UI
     * @return
     */
    public String getUserName()
    {

        String userName = userObservableField.get().getUserPhone();
        Log.i(TAG,"从变量到UI,userName="+userName);
        return userName;
    }

    /**
     * 双向绑定 用户名 之从UI到变量
     * @return
     */
    public void setUserName(String userName)
    {
        userObservableField.get().setUserPhone(userName);
        Log.i(TAG,"用户名双向绑定，从UI到变量,userName="+userObservableField.get().getUserPhone());
    }

    /**
     * 双向绑定 密码 之从变量到UI
     * @return
     */
    public String getPassword()
    {

        String password = userObservableField.get().getPassword();
        Log.i(TAG,"从变量到UI,password="+password);
        return password;
    }

    /**
     * 双向绑定 密码 之从UI到变量
     * @return
     */
    public void setPassword(String password)
    {
       // if(password != null && !password.equals(user.getPassword()))
        {
            userObservableField.get().setPassword(password);
            Log.i(TAG,"密码双向绑定，从UI到变量,password="+userObservableField.get().getPassword());
        }

    }
}
