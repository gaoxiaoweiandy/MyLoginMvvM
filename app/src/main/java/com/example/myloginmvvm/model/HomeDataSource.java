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
 * 访问首页接口：调用服务器接口获取列表数据
 */
public class HomeDataSource implements LifecycleObserver {
    private  String TAG = HomeDataSource.class.getSimpleName() ;
    private Subscription mSubscription;
    static HomeDataSource instance;

    /**
     * 获取DataSource单例
     * @return
     */
    static public HomeDataSource getSigleInstance() {

        synchronized(LoginDataSource.class) {
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


    /**
     * 使用LifeCycle自动调用：即当Activity进入OnStop生命周期时，会被LifecycleObserver感知到
     * 从而自动调用unSubscription函数来释放资源
     */
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