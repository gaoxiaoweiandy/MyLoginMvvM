package com.example.myloginmvvm.bean;

import java.util.ArrayList;

/**
 * 后台接口返回的列表数据,Json
 * 可能后台接口返回的数据结构设计的不好，敬请谅解。
 */

public class JsonDeviceListData {
    ArrayList<Device>deviceList = new ArrayList<Device>();
    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(ArrayList<Device> deviceList) {
        this.deviceList = deviceList;
    }


}
