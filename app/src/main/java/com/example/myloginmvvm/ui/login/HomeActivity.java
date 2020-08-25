package com.example.myloginmvvm.ui.login;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.myloginmvvm.R;
import com.example.myloginmvvm.ViewModelFactory;
import com.example.myloginmvvm.bean.Device;
import com.example.myloginmvvm.bean.JsonDeviceList;
import com.example.myloginmvvm.bean.User;
import com.example.myloginmvvm.databinding.ActivityHomeBinding;
import com.example.myloginmvvm.model.HomeDataSource;
import com.example.myloginmvvm.ui.login.adapter.MyDeviceListAdapter;
import com.example.myloginmvvm.vm.HomeViewModel;
import java.util.ArrayList;

/**
 * 首页：显示一个列表，这里为多条设备信息.
 */
public class HomeActivity extends AppCompatActivity {
    private String mUserName;
    private ListView lvDevices;
    private MyDeviceListAdapter mAdapter;
    private ArrayList<Device> mDeviceList = new ArrayList<Device>();
    private HomeViewModel homeViewModel;
    private ActivityHomeBinding dataBindingHome;
    String TAG = "AACHomeActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBindingHome = DataBindingUtil.setContentView(this,R.layout.activity_home);
        getLastIntent();
        initView();
        /*
        让HomeDataSource能感知Activity的生命周期，在Activity.onstop的时候，HomeDataSouce中的某些函数会自动执行,
        这里用到的正是Android JetPack架构组件中的 “LifeCycle”.
        */
        getLifecycle().addObserver(HomeDataSource.getSigleInstance());
        homeViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(HomeViewModel.class);

        //使用Android JetPack架构组件中的 “liveData”监听数据的变化，一旦有变化则让UI显示这些数据——观察者模式。
        initLiveDataObserve();
    }

    @Override
    protected void onStart() {
        super.onStart();
        User user = new User(getApplication());
        String token = user.getUserToken();
        //VM层去调遣Model层（Repository+DataSource）获取数据
        homeViewModel.getMyDeviceList(mUserName,token);
    }

    /**
     * 初始化UI
     * 使用DataBinding再也不需要findViewById了
     */
    private void initView() {
        mAdapter = new MyDeviceListAdapter(mDeviceList,this);
        dataBindingHome.lvDeviceList.setAdapter(mAdapter);
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
    public void updateDeviceList(JsonDeviceList userDeviceJson)
    {
        mDeviceList.clear();
        ArrayList<Device> deviceList =  userDeviceJson.getObjectbean().getDeviceList();
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
        homeViewModel.getDeviceListLiveData().observe(this, new Observer<JsonDeviceList>() {
            @Override
            public void onChanged(JsonDeviceList jsonDeviceList) {
                try
                {
                    if(jsonDeviceList.getStatus() == 1)
                    {
                        //更新列表数据
                        updateDeviceList(jsonDeviceList);
                    }
                    else {
                        String msg = jsonDeviceList.getMsg();
                        Toast.makeText(HomeActivity.this,msg,Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

}