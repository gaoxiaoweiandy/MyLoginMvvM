package com.example.myloginmvvm.model;
import androidx.lifecycle.MutableLiveData;
import com.example.myloginmvvm.bean.JsonDeviceListData;
import com.example.myloginmvvm.bean.Result;

import rx.Subscription;

/**
 * 可以将Repository和 DataSource这两者合并起来看作是MVVM中的Model层，实质最终获取服务起数据的是DataSource,
 * 这里引入Repository的目的是根据业务需求提前对服务器返回的数据进行加工处理（最终接近View层需要显示的数据结构），这样VM(ViewModel)层将会变得简洁，而DataSource
 * 只负责从服务器获取数据，达到获取数据模块化。
 */
public class HomeRepository {
    private static volatile HomeRepository instance;
    private HomeDataSource dataSource;
    private HomeRepository(HomeDataSource dataSource) {
        this.dataSource = dataSource;
    }
    /**
     * 获取单例
     * @return
     */
    public static HomeRepository getInstance(HomeDataSource dataSource) {
        if (instance == null) {
            instance = new HomeRepository(dataSource);
        }
        return instance;
    }

    /**
     * 首页列表接口，掉钱DataSource去专门负责从服务器接口获取数据
     * @param userName：用户名即mobile手机号
     * @param userToken：登录时返回的token
     * @param liveData: 要修改的LiveData数据，同时View层（HomeActivity）监听LiveData的数据变更，从而更新UI
     * @return
     */
    public Subscription getMyDeviceList(String userName, String userToken, MutableLiveData<Result<JsonDeviceListData>> liveData) {
        return dataSource.getMyDeviceList( userName ,userToken,liveData);
    }

}