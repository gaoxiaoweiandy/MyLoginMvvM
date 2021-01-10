package com.example.myloginmvvm.vm;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myloginmvvm.bean.JsonDeviceListData;
import com.example.myloginmvvm.bean.Result;
import com.example.myloginmvvm.model.HomeRepository;

import java.util.ArrayList;

import rx.Subscription;

/**
 * 首页的VM层，调用M层去获取数据，同时更改liveData的数据，从而View层（HomeActivity)就能观察到LiveData数据的
 * 变化，最后更新UI。
 */
public class HomeViewModel extends ViewModel {
    String TAG = "AACHomeViewModel";
    private MutableLiveData<Result<JsonDeviceListData>> deviceListLiveData = new MutableLiveData<Result<JsonDeviceListData>>();
    private HomeRepository homeRepository;
    private ArrayList<Subscription> subscriptions = new ArrayList<>();

    public HomeViewModel(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }
    public MutableLiveData<Result<JsonDeviceListData>> getDeviceListLiveData() {
        return deviceListLiveData;
    }

    /**
     * VM层调用M层（homeRepository+DataSource）
     * @param username： 用户名
     * @param token： 登录接口返回的token
     * @return
     */
    public Subscription getMyDeviceList(String username, String token) {
        Subscription  subscription = homeRepository.getMyDeviceList(username,token,deviceListLiveData);
        subscriptions.add(subscription);
        return subscription ;
    }

    @Override
    protected void onCleared() {
        Log.i(TAG,"onCleared");
        if(subscriptions != null) {
            for(int i = 0; i < subscriptions.size(); i++) {
                Subscription subscription = subscriptions.get(i);
                boolean isUn = subscription.isUnsubscribed();
                Log.i(TAG,"isUn="+isUn);
                if(!isUn)
                {
                    Log.i(TAG,"clear"+i+",name="+subscriptions.get(i).getClass());
                    subscriptions.get(i).unsubscribe();
                }
            }
            subscriptions.clear();
        }
        super.onCleared();
    }

}