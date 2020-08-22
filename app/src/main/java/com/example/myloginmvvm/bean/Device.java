package com.example.myloginmvvm.bean;

/**
 * Created by xw.gao on 2017/6/6.
 */

public class Device {
    public String deviceId;
    public String deviceIMEI;
    public String deviceName;
    public String deviceModel;
    public String latitude = "";//纬度
    public String longitude = "";//经度
    public int deviceType;//设备类型
    public float deviceVersion;
    public String deviceMobile;
    public int locationMode;
    public int timeInterval;
    public int alarmControl;
    public String deviceStatus;
    public String onlineTime;
    public String deviceCreateTime;
    public int deviceOwnerID;
    public int locationPower;    //电量百分比，5是百分百，1是百分之20
    public int alarmType;
    public long locationTime;
    public String ownerName;        //老人名称
    public String guardianId;           //绑定这个设备的 监护人 用户ID
    public long time;                //接口返回的时间
    public boolean isAlarming = false; //是否正在救助中，如果设置为救助中则更好位置MARKER图标
    public int isOnLine = -1;
    public int alarmStatus = 0;


    /**
     * 状态为1表示处于报警状态（需要救助或救助中），则更好位置Marker图标
     * @return
     */
    public int getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(int alarmStatus) {
        this.alarmStatus = alarmStatus;
    }


    /**
     * 设备在线状态，1在线
     * @return
     */
    public int getIsOnLine() {
        return isOnLine;
    }

    public void setIsOnLine(int isOnLine) {
        this.isOnLine = isOnLine;
    }
    /**
     * 标记该设备是否正在报警
     * @return
     */
    public boolean isAlarming() {
        return isAlarming;
    }

    public void setAlarming(boolean alarming) {
        isAlarming = alarming;
    }

    /**
     * 设备的 主人（绑定这个设备的用户）
     * @return
     */
    public String getGuardianId() {
        return guardianId;
    }

    public void setGuardianId(String guardianId) {
        this.guardianId = guardianId;
    }

    /**
     * 接口返回的时间
     * @return
     */
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getLocationPower() {
        return locationPower;
    }

    public void setLocationPower(int locationPower) {
        this.locationPower = locationPower;
    }

    public long getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(long locationTime) {
        this.locationTime = locationTime;
    }




    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }


    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public float getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(float deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getDeviceMobile() {
        return deviceMobile;
    }

    public void setDeviceMobile(String deviceMobile) {
        this.deviceMobile = deviceMobile;
    }

    public int getLocationMode() {
        return locationMode;
    }

    public void setLocationMode(int locationMode) {
        this.locationMode = locationMode;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    public int getAlarmControl() {
        return alarmControl;
    }

    public void setAlarmControl(int alarmControl) {
        this.alarmControl = alarmControl;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getDeviceCreateTime() {
        return deviceCreateTime;
    }

    public void setDeviceCreateTime(String deviceCreateTime) {
        this.deviceCreateTime = deviceCreateTime;
    }

    public int getDeviceOwnerID() {
        return deviceOwnerID;
    }

    public void setDeviceOwnerID(int deviceOwnerID) {
        this.deviceOwnerID = deviceOwnerID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

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

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }


}
