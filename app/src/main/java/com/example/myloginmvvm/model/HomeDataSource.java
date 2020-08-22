package com.example.myloginmvvm.model;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.myloginmvvm.bean.JsonDeviceList;
import com.example.myloginmvvm.net.RetrofitManager;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class HomeDataSource implements LifecycleObserver {
    private  String TAG = HomeDataSource.class.getSimpleName() ;
    private Subscription mSubscription;
    static HomeDataSource instance;

    static public HomeDataSource getSigleInstance() {

        synchronized(LoginDataSource.class) {
            if(instance == null) {
                instance = new HomeDataSource();
            }
        }
        return instance;
    }

    public MutableLiveData<JsonDeviceList> getMyDeviceList(String userName,String userToken,MutableLiveData<JsonDeviceList> liveData) {
        try {
            // TODO: handle loggedInUser authentication
            mSubscription = RetrofitManager.getApiService()
                    .getMyDeviceList(userName,userToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(data -> {
                        JsonDeviceList jsonDeviceList = (JsonDeviceList)data;
                        liveData.postValue(jsonDeviceList);
                        Log.i(TAG,jsonDeviceList.toString());
                    }, throwable -> {
                        JsonDeviceList jsonDeviceListException = createThrowableData(throwable);
                        liveData.postValue(jsonDeviceListException);
                    });
            return liveData;
        } catch (Exception e) {
            JsonDeviceList jsonDeviceListException = createThrowableData(e.getCause());
            liveData.postValue(jsonDeviceListException);
        }
        return liveData;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void unSubscription()
    {
        if(mSubscription != null)
        {
            mSubscription.unsubscribe();
            Log.i(TAG,"unSubscription");
        }
    }
    /**
     * 创建包含有异常信息的登录对象
     * @param throwable
     * @return
     */
    public JsonDeviceList createThrowableData(Throwable throwable)
    {
        JsonDeviceList jsonDeviceList = new JsonDeviceList();
        jsonDeviceList.setStatus(-1);
        jsonDeviceList.setThrowable(throwable);
        return jsonDeviceList;
    }
}