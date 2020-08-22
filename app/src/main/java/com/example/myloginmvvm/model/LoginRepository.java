package com.example.myloginmvvm.model;
import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.myloginmvvm.MyApplication;
import com.example.myloginmvvm.bean.JsonLogin;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;
    private LoginDataSource dataSource;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }


    public  MutableLiveData<JsonLogin> login(String username, String password, MutableLiveData<JsonLogin> jsonLoginLiveData, Application app) {
        // handle login
        MutableLiveData<JsonLogin> result = dataSource.login(username, password,jsonLoginLiveData, app);
        return result;
    }
}