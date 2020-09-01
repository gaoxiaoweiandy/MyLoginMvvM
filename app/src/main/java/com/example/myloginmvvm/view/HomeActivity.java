package com.example.myloginmvvm.view;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import com.example.myloginmvvm.R;
import com.example.myloginmvvm.bean.Device;
import com.example.myloginmvvm.bean.JsonDeviceListData;
import com.example.myloginmvvm.bean.Result;
import com.example.myloginmvvm.bean.User;
import com.example.myloginmvvm.databinding.ActivityHomeBinding;
import com.example.myloginmvvm.model.HomeDataSource;
import com.example.myloginmvvm.view.adapter.MyDeviceListAdapter;
import com.example.myloginmvvm.vm.HomeViewModel;
import java.util.ArrayList;

/**
 * 首页：显示一个列表，这里为多条设备信息.
 */
public class HomeActivity extends BaseActivity<HomeViewModel, ActivityHomeBinding> {
    String TAG  = "AACHomeActivity";
    private String mUserName;
    private MyDeviceListAdapter mAdapter;
    private ArrayList<Device> mDeviceList = new ArrayList<Device>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLastIntent();
        initView();
        //使用Android JetPack架构组件中的 “liveData”监听数据的变化，一旦有变化则让UI显示这些数据——观察者模式。
        initLiveDataObserve();
    }

    @Override
    protected void onStart() {
        super.onStart();
        User user = new User(getApplication());
        String token = user.getUserToken();
        //VM层去调遣Model层（Repository+DataSource）获取数据
        mViewModel.getMyDeviceList(mUserName,token);
    }

    @Override
    protected void onStop() {
        Log.i(TAG,"onStop");
        super.onStop();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_home;
    }

    /*
      让HomeDataSource能感知Activity的生命周期，在Activity.onstop的时候，HomeDataSouce中的某些函数会自动执行,
      这里用到的正是Android JetPack架构组件中的 “LifeCycle”.
      */
    @Override
    protected void addLifeCycleObserver() {
        getLifecycle().addObserver((LifecycleObserver) HomeDataSource.getSingleInstance());
    }

    /**
     * 初始化UI
     * 使用DataBinding再也不需要findViewById了
     */
    private void initView() {
        mAdapter = new MyDeviceListAdapter(mDeviceList,this);
        mDataBinding.lvDeviceList.setAdapter(mAdapter);
    }

    /**
     * 获取上页传递过来的数据
     */
    private void getLastIntent() {
        Intent i = this.getIntent();
        mUserName = i.getStringExtra("userName");
    }

    /**
     * 更新设备列表
     * @param userDeviceJson:服务器返回的JSON数据结构
     */
    public void updateDeviceList(JsonDeviceListData userDeviceJson)
    {
        mDeviceList.clear();
        ArrayList<Device> deviceList = userDeviceJson.getDeviceList();
        if(deviceList!=null && deviceList.size()>0)
        {
            mDeviceList.addAll(deviceList);
        }
        mAdapter.notifyDataSetChanged();
        return;
    }

    /**
     * LiveData观察数据的变化，从而更新UI
     */
    private void initLiveDataObserve() {

        mViewModel.getDeviceListLiveData().observe(this, new Observer<Result<JsonDeviceListData>>() {
            @Override
            public void onChanged(Result<JsonDeviceListData> result) {
                result.handler(new OnCallback<JsonDeviceListData>() {
                    @Override
                    public void onSuccess(JsonDeviceListData data) {
                        updateDeviceList(data);
                    }
                });
            }
        });
    }
}