package com.example.myloginmvvm.model.home;

import com.example.myloginmvvm.model.Result;
import com.example.myloginmvvm.model.bean.LoggedInUser;
import com.example.myloginmvvm.model.login.LoginDataSource;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class HomeRepository {

    private static volatile HomeRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private HomeRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static HomeRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new HomeRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }


    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

/*    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }*/
}