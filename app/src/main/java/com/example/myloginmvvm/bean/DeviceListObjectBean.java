package com.example.myloginmvvm.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/6.
 */

public class DeviceListObjectBean {
    String teamId;
    ArrayList<Device>deviceList = new ArrayList<Device>();

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(ArrayList<Device> deviceList) {
        this.deviceList = deviceList;
    }


}
