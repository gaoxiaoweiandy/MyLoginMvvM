package com.example.myloginmvvm.vm;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.myloginmvvm.bean.JsonDeviceList;
import com.example.myloginmvvm.model.HomeRepository;

public class HomeViewModel extends AndroidViewModel {
    String TAG = "AACHomeViewModel";
    private MutableLiveData<JsonDeviceList> deviceListLiveData = new MutableLiveData<>();
    private HomeRepository homeRepository;

    public HomeViewModel(HomeRepository homeRepository, Application app) {
        super(app);
        this.homeRepository = homeRepository;
    }
    public MutableLiveData<JsonDeviceList> getDeviceListLiveData() {
        return deviceListLiveData;
    }
    public MutableLiveData<JsonDeviceList>  getMyDeviceList(String username,String token) {
        MutableLiveData<JsonDeviceList> result = null;
        result = homeRepository.getMyDeviceList(username,token,deviceListLiveData);
        return result;
    }

}