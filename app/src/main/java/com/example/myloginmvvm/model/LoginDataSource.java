package com.example.myloginmvvm.model;

import android.app.Application;
import android.content.ContentValues;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.myloginmvvm.bean.JsonLogin;
import com.example.myloginmvvm.bean.User;
import com.example.myloginmvvm.net.RetrofitManager;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 访问登录接口：调用服务器登录接口，返回token等用户信息
 */
public class LoginDataSource implements LifecycleObserver {
    private Subscription mSubscription;
    String TAG = "AACLoginDataSource";
    static LoginDataSource instance;
    /**
     * 获取DataSource单例
     * @return
     */
     static public LoginDataSource  getSigleInstance() {
        if(instance == null) {
            synchronized (LoginDataSource.class) {
                instance = new LoginDataSource();
            }
        }
        return instance;
    }
    /**
     * 登录服务器接口
     * @param username：用户名即mobile手机号
     * @param password：登录密码
     * @param liveData: 要修改的LiveData数据，同时View层（LoginActivity）监听LiveData的数据变更，从而更新UI
     * @return
     */
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
                        saveUserData(jsonLogin.getData().getToken(),app);
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
     * 保存登录返回的token等 用户信息
     * @param token: 服务器登录接口返回的token
     * @param app: 全局context
     */
    public void saveUserData(String token,Application app)
    {
        //保存用户数据在本地SharePreference
        User user = new User();
        ContentValues value = new ContentValues();
        value.put("userToken",token);
        user.saveUserInfo(app,value);
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
    /**
     * 使用LifeCycle自动调用：即当Activity进入OnStop生命周期时，会被LifecycleObserver感知到
     * 从而自动调用unSubscription函数来释放资源
     */
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