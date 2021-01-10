package com.example.myloginmvvm.vm;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import android.app.Application;
import android.util.Log;

import com.example.myloginmvvm.MyApplication;
import com.example.myloginmvvm.bean.JsonLoginData;
import com.example.myloginmvvm.bean.Result;
import com.example.myloginmvvm.model.LoginRepository;

import java.util.ArrayList;

import rx.Subscription;

/**
 * 登录页面的VM层，调用M层去获取数据，同时更改liveData的数据，从而View层（LoginActivity)就能观察到LiveData数据的
 * 变化，最后更新UI。
 */
public class LoginViewModel extends AndroidViewModel {

    String TAG = "AACLoginViewModel";
    private MutableLiveData<Result<JsonLoginData>> jsonLoginLiveData = new MutableLiveData<>();
    private LoginRepository loginRepository; //M层引用
    private MyApplication mApp;
    ArrayList<Subscription> subscriptions = new ArrayList<>();


    /**
     * 构造函数
     * @param app：为了防止内存泄露，传递一个Application作为context
     * @param loginRepository: M层对象实例
     */
    public LoginViewModel(Application app,LoginRepository loginRepository) {
        super(app);
        this.mApp = getApplication();
        this.loginRepository = loginRepository;
    }
    public MutableLiveData<Result<JsonLoginData>> getJsonLogin() {
        return jsonLoginLiveData;
    }
    /**
     * VM层调用M层（LoginRepository+DataSource）
     * @param username： 用户名
     * @param password： 密码
     * @return
     */
    public Subscription login(String username, String password) {
        Subscription subscription = loginRepository.login(username, password,jsonLoginLiveData,mApp);
        subscriptions.add(subscription);
        return subscription;
    }

    @Override
    protected void onCleared() {
        Log.i(TAG,"onCleared");
        if(subscriptions != null) {
            for(int i = 0; i < subscriptions.size(); i++) {
                Subscription subscription = subscriptions.get(i);
                boolean isUn = subscription.isUnsubscribed();
                Log.i(TAG,"isUn="+isUn);
                if(!isUn)
                {
                    Log.i(TAG,"clear"+i+",name="+subscriptions.get(i).getClass());
                    subscriptions.get(i).unsubscribe();
                }
            }
            subscriptions.clear();
        }
        super.onCleared();
    }
}