package com.example.myloginmvvm.model;

import android.app.Application;
import android.content.ContentValues;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.myloginmvvm.MyApplication;
import com.example.myloginmvvm.bean.JsonLogin;
import com.example.myloginmvvm.bean.LoginData;
import com.example.myloginmvvm.bean.User;
import com.example.myloginmvvm.net.RetrofitManager;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource implements LifecycleObserver {
    private Subscription mSubscription;
    String TAG = "AACLoginDataSource";
    static LoginDataSource instance;

     static public LoginDataSource  getSigleInstance() {
        if(instance == null) {
            synchronized (LoginDataSource.class) {
                instance = new LoginDataSource();
            }
        }
        return instance;
    }

    public MutableLiveData<JsonLogin> login(String username, String password, MutableLiveData<JsonLogin> liveData, Application app) {
        try {
            // TODO: handle loggedInUser authentication
            mSubscription = RetrofitManager.getApiService()
                    .login(username,password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> {
                        Log.i(TAG,"subscribe");
                        //保存用户数据在本地SharePreference

                        JsonLogin jsonLogin = (JsonLogin)o;
                        liveData.postValue(jsonLogin);
                    }, throwable -> {
                        JsonLogin jsonLogin = createThrowableLoginData(throwable);
                        liveData.postValue(jsonLogin);
                    });
            return liveData;
        } catch (Exception e) {
            JsonLogin jsonLoginException = createThrowableLoginData(e.getCause());
            liveData.postValue(jsonLoginException);
        }

        Log.i(TAG,"subscribe2");
        return liveData;
    }

    /**
     * 创建包含有异常信息的登录对象
     * @param throwable
     * @return
     */
    public JsonLogin createThrowableLoginData(Throwable throwable)
    {
        JsonLogin jsonLogin = new JsonLogin();
        jsonLogin.setStatus(-1);
        jsonLogin.setThrowable(throwable);
        return jsonLogin;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void unSubscription()
    {
        if(mSubscription != null)
        {
            mSubscription.unsubscribe();
            Log.i(TAG,"unSubscription");
        }
    }


}