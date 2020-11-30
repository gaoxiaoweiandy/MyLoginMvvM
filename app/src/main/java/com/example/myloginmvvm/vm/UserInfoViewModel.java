package com.example.myloginmvvm.vm;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myloginmvvm.MyApplication;
import com.example.myloginmvvm.bean.JsonLoginData;
import com.example.myloginmvvm.bean.Result;
import com.example.myloginmvvm.model.LoginRepository;

/**
 * 登录页面的VM层，调用M层去获取数据，同时更改liveData的数据，从而View层（LoginActivity)就能观察到LiveData数据的
 * 变化，最后更新UI。
 */
public class UserInfoViewModel extends ViewModel {

    String TAG = "AACLoginViewModel";
    private MutableLiveData<Result<JsonLoginData>> jsonLoginLiveData = new MutableLiveData<>();
    private LoginRepository loginRepository; //M层引用
    private MyApplication mApp;

    /**
     * 构造函数
     * @param loginRepository: M层对象实例
     */
    public UserInfoViewModel(LoginRepository loginRepository) {
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
    public MutableLiveData<Result<JsonLoginData>> login(String username, String password) {
        jsonLoginLiveData = loginRepository.login(username, password,jsonLoginLiveData,mApp);
        return jsonLoginLiveData;
    }
}