package com.example.myloginmvvm.ui.login;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.myloginmvvm.R;
import com.example.myloginmvvm.ViewModelFactory;
import com.example.myloginmvvm.bean.Device;
import com.example.myloginmvvm.bean.JsonDeviceList;
import com.example.myloginmvvm.bean.User;
import com.example.myloginmvvm.model.HomeDataSource;
import com.example.myloginmvvm.model.bean.LoggedInUserView;
import com.example.myloginmvvm.ui.login.adapter.MyDeviceListAdapter;
import com.example.myloginmvvm.vm.HomeViewModel;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private String mUserName;
    private ListView lvDevices;
    private MyDeviceListAdapter mAdapter;
    private ArrayList<Device> mDeviceList = new ArrayList<Device>();
    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getLastIntent();
        initView();
        getLifecycle().addObserver(HomeDataSource.getSigleInstance());
        homeViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(HomeViewModel.class);
        initLiveDataObserve();

        User user = new User(this);
        String token = user.getUserToken();
        homeViewModel.getMyDeviceList(mUserName,token);
    }

    private void getLastIntent() {
        Intent i = this.getIntent();
        mUserName = i.getStringExtra("userName");
    }

    /**
     * 更新设备列表
     * @param userDeviceJson
     */
    public void updateGuiDeviceList(JsonDeviceList userDeviceJson)
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
    private void initLiveDataObserve() {
        homeViewModel.getDeviceListLiveData().observe(this, new Observer<JsonDeviceList>() {
            @Override
            public void onChanged(JsonDeviceList jsonDeviceList) {
                try
                {
                    if(jsonDeviceList.getStatus() == 1)
                    {
                        //获取成功更新GUI
                        updateGuiDeviceList(jsonDeviceList);
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

    private void initView() {
        lvDevices = this.findViewById(R.id.lvDeviceList);
        mAdapter = new MyDeviceListAdapter(mDeviceList,this);
        lvDevices.setAdapter(mAdapter);
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}