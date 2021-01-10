package com.example.myloginmvvm.model;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.myloginmvvm.bean.JsonDeviceListData;
import com.example.myloginmvvm.bean.Result;
import com.example.myloginmvvm.net.RetrofitManager;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * 访问首页接口：调用服务器接口获取列表数据
 */
public class HomeDataSource extends BaseDataSource   {
    private  String TAG = "AAC" + HomeDataSource.class.getSimpleName() ;
    static HomeDataSource instance;

    /**
     * 获取DataSource单例
     * @return
     */
    static public HomeDataSource getSingleInstance() {

        synchronized(HomeDataSource.class) {
            if(instance == null) {
                instance = new HomeDataSource();
            }
        }
        return instance;
    }

    /**
     * 首页列表接口
     * @param userName：用户名即mobile手机号
     * @param userToken：登录时返回的token
     * @param liveData: 要修改的LiveData数据，同时View层（HomeActivity）监听LiveData的数据变更，从而更新UI
     * @return
     */
    public Subscription getMyDeviceList(String userName, String userToken, MutableLiveData<Result<JsonDeviceListData>> liveData) {
        Subscription mSubscription = null;
        try {
            // TODO: handle loggedInUser authentication
               mSubscription = RetrofitManager.getApiService()
                    .getMyDeviceList(userName,userToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            liveData.postValue(Result.loading(""));
                            Log.i(TAG,"doOnSubscribe");
                        }
                    })
                    .subscribe(data -> {

                        Log.i(TAG,data.toString());
                        liveData.postValue(Result.response2(data));
                    }, throwable -> {
                        liveData.postValue(Result.error(throwable));
                    });
            return mSubscription;
        } catch (Exception e) {
            liveData.postValue(Result.error(e.getCause()));
        }
        return mSubscription;
    }
}