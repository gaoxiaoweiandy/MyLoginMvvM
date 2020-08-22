package com.example.myloginmvvm.vm;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import android.content.ContentValues;
import android.util.Log;
import android.util.Patterns;

import com.example.myloginmvvm.MyApplication;
import com.example.myloginmvvm.bean.JsonLogin;
import com.example.myloginmvvm.bean.User;
import com.example.myloginmvvm.model.LoginRepository;
import com.example.myloginmvvm.R;

public class LoginViewModel extends AndroidViewModel {

    String TAG = "AACLoginViewModel";
    private MutableLiveData<JsonLogin> jsonLoginLiveData = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private Application mApp;

    public LoginViewModel(Application app,LoginRepository loginRepository) {
        super(app);
        this.mApp = app;
        this.loginRepository = loginRepository;
    }

    public MutableLiveData<JsonLogin> getJsonLogin() {
        return jsonLoginLiveData;
    }

    public LiveData<JsonLogin> login(String username, String password) {
        jsonLoginLiveData = loginRepository.login(username, password,jsonLoginLiveData,mApp);
        Log.i(TAG,"login");
        return jsonLoginLiveData;
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}