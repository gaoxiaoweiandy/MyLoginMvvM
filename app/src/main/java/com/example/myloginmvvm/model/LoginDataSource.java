package com.example.myloginmvvm.model;
import android.app.Application;
import android.content.ContentValues;
import androidx.lifecycle.MutableLiveData;
import com.example.myloginmvvm.bean.JsonLogin;
import com.example.myloginmvvm.bean.User;
import com.example.myloginmvvm.net.RetrofitManager;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 访问登录接口：调用服务器登录接口，返回token等用户信息
 */
public class LoginDataSource extends BaseDataSource  {
    String TAG = "AACLoginDataSource";
    static LoginDataSource instance;

    /**
     * 获取DataSource单例
     * @return
     */
     static public LoginDataSource  getSingleInstance() {
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
                        //保存用户数据在本地SharePreference
                        JsonLogin jsonLogin = (JsonLogin)o;
                        saveUserData(jsonLogin.getData().getToken(),app);
                        liveData.postValue(jsonLogin);
                    }, throwable -> {
                        JsonLogin jsonLogin = createThrowableData(new JsonLogin(),throwable);
                        liveData.postValue(jsonLogin);
                    });
            return liveData;
        } catch (Exception e) {
            JsonLogin jsonLoginException = createThrowableData(new JsonLogin(),e.getCause());
            liveData.postValue(jsonLoginException);
        }

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
}