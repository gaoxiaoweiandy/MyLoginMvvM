package com.example.myloginmvvm.vm;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myloginmvvm.MyApplication;
import com.example.myloginmvvm.bean.JsonLoginData;
import com.example.myloginmvvm.bean.Result;
import com.example.myloginmvvm.model.LoginRepository;

import java.io.File;

/**
 * 登录页面的VM层，调用M层去获取数据，同时更改liveData的数据，从而View层（LoginActivity)就能观察到LiveData数据的
 * 变化，最后更新UI。
 */
public class UserInfoViewModel extends ViewModel {

    String TAG = "AACLoginViewModel";
    private MutableLiveData<Result<String>> jsonPostBoundList = new MutableLiveData<>();
    private LoginRepository loginRepository; //M层引用

    /**
     * 构造函数
     * @param loginRepository: M层对象实例
     */
    public UserInfoViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }
    public MutableLiveData<Result<String>> getPostBoundList() {
        return jsonPostBoundList;
    }

    /**
     * @param name: 姓名
     * @param file：图片文件
     * @return
     */
    public MutableLiveData<Result<String>> postPoundList(String name,File file,String token) {
        jsonPostBoundList = loginRepository.postBoundList(name, jsonPostBoundList,file,token);
        return jsonPostBoundList;
    }
}