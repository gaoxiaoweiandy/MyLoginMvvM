package com.example.myloginmvvm.bean;

/**
 * Created by xw.gao on 2017/6/6.
 * 类对象与服务器下发的JSON对应，可直接将JSON字段映射到类成员，无需解析JSON
 */

public class JsonDeviceList extends DataCommon{

    DeviceListObjectBean objectbean;

    public DeviceListObjectBean getObjectbean() {
        return objectbean;
    }

    public void setObjectbean(DeviceListObjectBean objectbean) {
        this.objectbean = objectbean;
    }

}


