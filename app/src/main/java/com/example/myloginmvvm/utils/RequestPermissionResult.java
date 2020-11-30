package com.example.myloginmvvm.utils;

import com.tbruyelle.rxpermissions2.Permission;

public interface RequestPermissionResult
{
    abstract void  onPermissionGranted(Permission permission);
    abstract void  onPermissionDenied(Permission permission,int DeniedType);
}