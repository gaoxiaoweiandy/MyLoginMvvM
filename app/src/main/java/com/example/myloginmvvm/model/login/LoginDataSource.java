package com.example.myloginmvvm.model.login;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.myloginmvvm.bean.JsonLogin;
import com.example.myloginmvvm.model.Result;
import com.example.myloginmvvm.model.bean.LoggedInUser;
import com.example.myloginmvvm.net.RetrofitManager;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource implements LifecycleObserver {
    private Subscription mSubscription;
    String TAG = "LoginDataSource";
    static LoginDataSource instance;

     static public LoginDataSource  getSigleInstance() {
        if(instance == null) {
            synchronized (LoginDataSource.class) {
                instance = new LoginDataSource();
            }
        }
        return instance;
    }

    public MutableLiveData<JsonLogin> login(String username, String password, MutableLiveData<JsonLogin> liveData) {
        try {
            // TODO: handle loggedInUser authentication
            mSubscription = RetrofitManager.getApiService()
                    .login(username,password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> {
                        JsonLogin jsonLogin = (JsonLogin)o;
                        liveData.postValue(jsonLogin);
                    }, throwable -> {
                        JsonLogin jsonLogin = createThrowableLoginData(throwable);
                        liveData.postValue(jsonLogin);
                    });
            return liveData;
        } catch (Exception e) {
            JsonLogin jsonLogin = createThrowableLoginData(e.getCause());
            liveData.postValue(jsonLogin);
        }
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