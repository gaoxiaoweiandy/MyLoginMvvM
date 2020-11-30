package com.example.myloginmvvm.utils;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import com.example.myloginmvvm.bean.Device;
import com.example.myloginmvvm.bean.MyConstant;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.io.File;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by baidu on 17/1/23.
 */

public class CommonUtil {

    private static DecimalFormat df = new DecimalFormat("######0.00");

    public static final double DISTANCE = 0.0001;
    private static String TAG = "CommonUtil";





    public static InputFilter newFilter(final Context context)
    {
        InputFilter inputFilter=new InputFilter() {


            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]|[\ud800\udc00-\udbff\udfff\ud800-\udfff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                Matcher matcher=  emoji.matcher(charSequence);
                if(!matcher.find()){
                    return null;
                }else{
                    Toast.makeText(context,  "非法字符",Toast.LENGTH_SHORT).show();
                    return "";
                }

            }
        };

        return inputFilter;
    }
    /**
     * 检查结束时间 是否大于开始时间
     * @param startDate : 开始日期
     * @param startTime： 开始时分
     * @param endDate：   结束日期
     * @param endTime：   结束时分
     * @return
     */
    static public boolean checkEndTimeOK(String startDate,String startTime,String endDate,String endTime)
    {

        String start = startDate+" "+startTime+":00";
        String end = startDate+" "+endTime+":00";

        long startTimeStamp = CommonUtil.toTimeStamp2(start);
        long endTimeStamp = CommonUtil.toTimeStamp2(end);

        if(endTimeStamp <= startTimeStamp)
        {

            return false;
        }

        return true;
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }

    /**
     * 获取当前时间戳(单位：秒)
     *
     * @return
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }


    /**
     * 获取当前时间戳(单位：毫秒)
     *
     * @return
     */
    public static long getCurrentTimeMs() {
        return System.currentTimeMillis();
    }

    /**
     * 校验double数值是否为0
     *
     * @param value
     * @return
     */
    public static boolean isEqualToZero(double value) {
        return Math.abs(value - 0.0) < 0.01 ? true : false;
    }

    /**
     * 经纬度是否为(0,0)点
     *
     * @return
     */
    public static boolean isZeroPoint(double latitude, double longitude) {
        return isEqualToZero(latitude) && isEqualToZero(longitude);
    }

    /**
     * 将字符串转为时间戳
     */
    public static long toTimeStamp(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA);
        Date date;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        return date.getTime() / 1000;
    }


    /**
     * 将字符串转为时间戳，鹰眼需要单位是秒所以除以1000gxw+
     */
    public static long toTimeStamp2(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA);
        Date date;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        return date.getTime();
    }


    /**
     * 获取时分秒
     *
     * @param timestamp 时间戳（单位：毫秒）
     * @return
     */
    public static String getHMS(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            return sdf.format(new Timestamp(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(timestamp);
    }

    /**
     * 获取年月日 时分秒
     *
     * @param timestamp 时间戳（单位：毫秒）
     * @return
     */
    public static String formatTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.format(new Timestamp(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(timestamp);
    }


    /**
     * 获取年月日 时分秒中间+“/”
     * @param timestamp
     * @return
     */
    public static String getDateTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
        try {
            return sdf.format(new Timestamp(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(timestamp);
    }


    /**
     * 获取年月日 时分秒中间+“/”
     * @param timestamp
     * @return
     */
    public static String getDateTimeYmdHms(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
        try {
            return sdf.format(new Timestamp(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(timestamp);
    }
    /**
     * 获取年月日 时分秒中间+“/”
     * @param timestamp
     * @return
     */
    public static String getDateTimeYMD(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.format(new Timestamp(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(timestamp);
    }



    public static String formatSecond(int second) {
        String format = "%1$,02d:%2$,02d:%3$,02d";
        Integer hours = second / (60 * 60);
        Integer minutes = second / 60 - hours * 60;
        Integer seconds = second - minutes * 60 - hours * 60 * 60;
        Object[] array = new Object[]{hours, minutes, seconds};
        return String.format(format, array);
    }

    public static final String formatDouble(double doubleValue) {
        return df.format(doubleValue);
    }

    /**
     * 计算x方向每次移动的距离
     */
    public static double getXMoveDistance(double slope) {
        if (slope == Double.MAX_VALUE) {
            return DISTANCE;
        }
        return Math.abs((DISTANCE * slope) / Math.sqrt(1 + slope * slope));
    }









    /**
     * 获得当天0点时间
     */
    /**
     * 获得当天0点时间
     */
    public static long getTimesmorningMs() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (long) (cal.getTimeInMillis());
    }


    /**
     * 获得当天24点时间
     */
    public static int getTimesnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }



    /**
     * 获取 APP版本名称versionName
     */

    public static String getVersionName(Context context){
        //getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageManager packageManager = context.getPackageManager();
        try
        {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),
                0);
            return packInfo.versionName;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 APP版本ID号： versionCode
     */
    public static int getVersionCode(Context context){

        try
        {
            //getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }






    static int mLocalVersionCode = -1;
    static  ProgressDialog mProgressDialog;




    /**
     * 安装APK
     * @param file
     */
    public static void  install(File file,Context context)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        context.startActivity(intent);
        return;
    }


    public static boolean isMobileCorrect(String mobileNum)
    {
        String regExp = "^1[3|4|5|6|7|8|9][0-9]{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobileNum);
        return m.find();
    }


    /**
     * 验证密码 格式
     * @param pwd
     * @param context
     * @param type
     * @return
     */
    public static boolean checkPwdNums(String pwd,Context context,int type)
    {
        String tip = "密码";

        switch (type)
        {
            case 0:
                tip = "密码";
                break;
            case 1:
                tip = "原密码";
                break;
            case 2:
                tip = "新密码";
                break;
            case 3:
                tip = "确认密码";
                break;
            default:
                tip = "密码";
                break;
        }

        if(TextUtils.isEmpty(pwd))
        {
            Toast.makeText(context,tip + "不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }




        if(pwd.length() < 6)
        {
            Toast.makeText(context,tip + "不能小于6位",Toast.LENGTH_SHORT).show();
            return false;

        }
        else if(pwd.length() > 16)
        {
            Toast.makeText(context,tip + "不能大于16位",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /**
     * 替换字符串中的占位变量
     * @param str     占位字符串,如"您的被监护对象{0},佩戴的{1}（设备编号：{2}）{3}"
     * @param params  各占位 的值
     * @return
     */
    public static  String repString(String str,String ... params){
        for(int i=0;i<params.length;i++){
            str=str.replace("{"+i+"}", params[i]==null?null:params[i]);
        }
        return str;
    }


    /**
     * 根据设备类型编号 获得 类型名称
     * @param type 设备类型
     * @return
     */
    public static String getDeviceTypeName(int type)
    {
        String name;

        switch (type)
        {

            case 1:
                name = "平安符定位器";
                break;
            case 2:
                name = "GSM跌倒报警器";
                break;
            case 3:
                name = "BLE跌倒报警器";
                break;
            case 4:
                name = "BLE手机支架";
                break;
           default:
               name = "未知设备";
                break;
        }
        return name;
    }

    /**
     * 检查经纬度是否正确
     * @param lat
     * @param lng
     * @return true表示正确，false表示不正确
     */
    public static  boolean checkLatLng(double lat,double lng)
    {
        if(lat <= 0.0 || lng <= 0.0)
        {
            return false;
        }
        return true;
    }

    /**
     * This filter will constrain edits not to make the length of the text
     * greater than the specified length.
     */
    public static class MyLengthFilter implements InputFilter {
        private int mMaxLength;
        Toast toast;
        String strEditType;
        private int mEditType;
        Context mContext;
        public MyLengthFilter(Context context,int max,int editType) {
            mMaxLength = max;

            mEditType = editType;
            mContext = context;

            switch (mEditType)
            {
                case 0:
                    strEditType = "密码";
                    break;
                case 1:
                    strEditType = "姓名";
                    break;
                case 2:
                    strEditType = "姓";
                    break;
                case 3:
                    strEditType = "名";
                    break;
                case 4:
                    strEditType = "地址";
                    break;
                case 5:
                    strEditType = "手机号";
                    break;
                case 6:
                    strEditType = "设备号";
                    break;

                case 7:
                    strEditType = "电话号码";
                    break;

                case 8:
                    strEditType = "群名称";
                    break;
                case 9:
                    strEditType = "围栏名称";
                    break;

                default:
                    strEditType = "输入";

            }


            //gxw-   toast.setGravity(Gravity.TOP, 0, 235);
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                   int dstart, int dend) {
            int keep = mMaxLength - (dest.length() - (dend - dstart));

            if(keep < (end - start)){
                toast = Toast.makeText(mContext,strEditType+"不能超过"+mMaxLength+"个字符",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0,0 );
                toast.show();
            }

            if (keep <= 0) {
                return "";
            } else if (keep >= end - start) {
                return filterEmoj(source);//gxw+
                //gxw- return null; // keep original
            } else {
                keep += start;
                if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                    --keep;
                    if (keep == start) {
                        return "";
                    }
                }
                return source.subSequence(start, keep);
            }
        }

        /**
         * @return the maximum length enforced by this input filter
         */
        public int getMax() {
            return mMaxLength;
        }

        public CharSequence filterEmoj(CharSequence charSequence)
        {

            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]|[\ud800\udc00-\udbff\udfff\ud800-\udfff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            Matcher matcher=  emoji.matcher(charSequence);
            if(!matcher.find()){
                return null;
            }else{
                return "";
            }
        }
    }
}
