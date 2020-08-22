package com.example.myloginmvvm.model;

import androidx.lifecycle.MutableLiveData;

import com.example.myloginmvvm.bean.JsonDeviceList;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class HomeRepository {
    private static volatile HomeRepository instance;
    private HomeDataSource dataSource;
    private HomeRepository(HomeDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static HomeRepository getInstance(HomeDataSource dataSource) {
        if (instance == null) {
            instance = new HomeRepository(dataSource);
        }
        return instance;
    }
    public MutableLiveData<JsonDeviceList> getMyDeviceList(String userName,String userToken,MutableLiveData<JsonDeviceList> liveData) {
        return dataSource.getMyDeviceList( userName ,userToken,liveData);
    }

}