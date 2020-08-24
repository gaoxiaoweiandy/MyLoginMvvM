package com.example.myloginmvvm.bean;


/**
 * 首页，列表接口返回的JSON数据结构对应的类
 */
public class JsonDeviceList extends DataCommon{

    JsonDeviceListObjectBean objectbean;

    public JsonDeviceListObjectBean getObjectbean() {
        return objectbean;
    }

    public void setObjectbean(JsonDeviceListObjectBean objectbean) {
        this.objectbean = objectbean;
    }

}


