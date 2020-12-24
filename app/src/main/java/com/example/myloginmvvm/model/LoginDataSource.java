package com.example.myloginmvvm.model;
import android.app.Application;
import android.content.ContentValues;
import androidx.lifecycle.MutableLiveData;
import com.example.myloginmvvm.bean.JsonDataCommon;
import com.example.myloginmvvm.bean.JsonLoginData;
import com.example.myloginmvvm.bean.Result;
import com.example.myloginmvvm.bean.User;
import com.example.myloginmvvm.net.RetrofitManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * 访问登录接口：调用服务器登录接口，返回token等用户信息
 */
public class LoginDataSource extends BaseDataSource  {
    String TAG = "AACLoginDataSource";
    static LoginDataSource instance;

    /**
     * 获取DataSource单例
     * @return
     */
     static public LoginDataSource  getSingleInstance() {
        if(instance == null) {
            synchronized (LoginDataSource.class) {
                instance = new LoginDataSource();
            }
        }
        return instance;
    }
    /**
     * 登录服务器接口
     * @param username：用户名即mobile手机号
     * @param password：登录密码
     * @param liveData: 要修改的LiveData数据，同时View层（LoginActivity）监听LiveData的数据变更，从而更新UI
     * @return
     */
    public MutableLiveData<Result<JsonLoginData>> login(String username, String password, MutableLiveData<Result<JsonLoginData>> liveData, Application app) {
        try {
            // TODO: handle loggedInUser authentication
            mSubscription = RetrofitManager.getApiService()
                    .login(username,password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            liveData.postValue(Result.loading(""));
                        }
                    })
                    .subscribe(data -> {
                        //保存用户数据在本地SharePreference
                        saveUserData(data.getData().getToken(),app);
                        liveData.postValue(Result.response(data));
                    }, throwable -> {

                        liveData.postValue(Result.error(throwable));
                    });
            return liveData;
        } catch (Exception e) {
            liveData.postValue(Result.error(e.getCause()));
        }

        return liveData;
    }


    /**
     * 登录服务器接口

     * @param jsonPostPoundList: 要修改的LiveData数据，同时View层（LoginActivity）监听LiveData的数据变更，从而更新UI
     * @return
     */
    public MutableLiveData<Result<String>> postPoundList(String name, MutableLiveData<Result<String>> jsonPostPoundList, File file,String token) {
        try {




            RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), file); //OK
            //设置图片格式
            RequestBody requestFile = RequestBody.create(MediaType.parse("image*//*"), file); //ok
            //设置一个file文件
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile1);//OK
            MultipartBody.Part filePart2 = MultipartBody.Part.createFormData("name", "an");//文本OK*/

            /*
               Map<String, RequestBody> requestBodyMap = new HashMap<>();

                RequestBody requestBody = RequestBody.create(MediaType.parse("image*//*"), file);
                // 这里前面一部分是服务器要求传的key("file")，加上一个i,就可以动态设置key的长度
                requestBodyMap.put("avatar", requestBody);*/

            mSubscription = RetrofitManager.getApiService()
                    .postPoundList(filePart,filePart2,token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            jsonPostPoundList.postValue(Result.loading(""));
                        }
                    })
                    .subscribe(data -> {
                        jsonPostPoundList.postValue(Result.response(data));
                    }, throwable -> {

                        jsonPostPoundList.postValue(Result.error(throwable));
                    });
            return jsonPostPoundList;
        } catch (Exception e) {
            jsonPostPoundList.postValue(Result.error(e.getCause()));
        }

        return jsonPostPoundList;
    }



    /**
     * 保存登录返回的token等 用户信息
     * @param token: 服务器登录接口返回的token
     * @param app: 全局context
     */
    public void saveUserData(String token,Application app)
    {
        //保存用户数据在本地SharePreference
        User user = new User();
        ContentValues value = new ContentValues();
        value.put("userToken",token);
        user.saveUserInfo(app,value);
    }
}