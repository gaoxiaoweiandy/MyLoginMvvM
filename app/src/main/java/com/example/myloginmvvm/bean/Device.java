package com.example.myloginmvvm.bean;

/**
 * 后台接口返回的 "列表数据中"的"列表项"数据
 */

public class Device {
    public String deviceIMEI;  //设备IMEI号
    public String deviceName;  //设备名称

    public String getDeviceIMEI() {
        return deviceIMEI;
    }

    public void setDeviceIMEI(String deviceIMEI) {
        this.deviceIMEI = deviceIMEI;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }



}
