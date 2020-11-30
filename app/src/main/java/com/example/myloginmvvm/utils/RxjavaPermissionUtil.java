package com.example.myloginmvvm.utils;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.example.myloginmvvm.R;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.functions.Consumer;

public class RxjavaPermissionUtil {
    String TAG = "RxjavaPermissionUtil";
    final int DENIED_SHOW_NEXT = 1; //下次还可以询问
    final int DENIED_SHOW_NO = 2;//以后再也不提醒了
    private  static RxjavaPermissionUtil instance;
    RequestPermissionResult mRequestPermissionResult;


    public void setmRequestPermissionResult(RequestPermissionResult mRequestPermissionResult) {
        this.mRequestPermissionResult = mRequestPermissionResult;
    }

    private RxjavaPermissionUtil()
    {

    }
    public synchronized static RxjavaPermissionUtil getInstance()
    {
        if(instance == null) {
            instance = new RxjavaPermissionUtil();
        }
        return instance;
    }
    public void checkPermissions(Activity activity, String [] permissions) {
        setmRequestPermissionResult((RequestPermissionResult) activity);
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions
                .requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            MyLogCat.i(TAG,"testRxPermission CallBack onPermissionsGranted() : "+permission.name+
                                    " request granted , to do something...");
                            mRequestPermissionResult.onPermissionGranted(permission);
                        }
                        else if (permission.shouldShowRequestPermissionRationale) {
                            MyLogCat.e(TAG,"testRxPermission CallBack onPermissionsDenied() : " + permission.name + "request denied");
                            //gxw-Toast.makeText(ScanBlueToothActivity.this, "拒绝权限，等待下次询问哦", Toast.LENGTH_SHORT).show();
                            mRequestPermissionResult.onPermissionDenied(permission,DENIED_SHOW_NEXT);
                            //todo request permission again
                        }
                        else {
                            MyLogCat.e(TAG,"testRxPermission CallBack onPermissionsDenied() : this " + permission.name + " is denied " +
                                    "and never ask again");
                            //gxw-Toast.makeText(ScanBlueToothActivity.this, "拒绝权限，不再弹出询问框，请前往APP应用设置中打开此权限", Toast.LENGTH_SHORT).show();
                            mRequestPermissionResult.onPermissionDenied(permission,DENIED_SHOW_NO);
                        }
                    }
                });
    }


    /*
     * 提醒打开导航权限
     * @param permission
     */
    AlertDialog mNavDidlog;
    public void alertOpenNavPermission(final Context context, String[] permission)
    {
        if(mNavDidlog != null && mNavDidlog.isShowing()) {
            mNavDidlog.dismiss();
        }

        String nav_permission_dlg_tip = context.getResources().getString(R.string.nav_permission_dlg_tip);
        String tipMsg = nav_permission_dlg_tip;

        for(String denied : permission) {
            if(denied.equals( Manifest.permission.ACCESS_FINE_LOCATION)) {
                denied = "位置信息权限";
            }
            else if(denied.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                denied = "存储权限";
            }
            else if(denied.equals(Manifest.permission.CALL_PHONE))
            {
                denied = "电话权限";
            }
            else if(denied.equals(Manifest.permission.CAMERA))
            {
                denied = "相机权限";
            }
            else if(denied.equals(Manifest.permission.RECORD_AUDIO))
            {
                denied = "麦克风";
            }
            tipMsg += denied+",";
        }
        tipMsg = tipMsg.substring(0,tipMsg.length()-1);
        tipMsg+=".";

        AlertDialog.Builder gpsDlgBuilder = new AlertDialog.Builder(context);
        gpsDlgBuilder.setTitle(R.string.notifyTitle)
                .setMessage(tipMsg)
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                .setPositiveButton(R.string.settings,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent();
                                intent.setData(Uri.parse("package:" + context.getPackageName()));
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                context.startActivity(intent);
                            }
                        })
                .setCancelable(false);

        mNavDidlog = gpsDlgBuilder.create();
        mNavDidlog.show();

        int colorPrimary = context.getResources().getColor(R.color.colorPrimary);
        int colorDescribe = context.getResources().getColor(R.color.describe_color);
        mNavDidlog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(colorPrimary);
        mNavDidlog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(colorDescribe);
    }

}
