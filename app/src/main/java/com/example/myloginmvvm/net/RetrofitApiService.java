package com.example.myloginmvvm.net;
import com.example.myloginmvvm.bean.JsonDeviceList;
import com.example.myloginmvvm.bean.JsonLogin;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by leo
 * on 2019/8/14.
 * Retrofit 接口请求配置都在这
 */
public interface RetrofitApiService {


    @POST("authorization/guardian/login")
    Observable<JsonLogin> login(
                                @Query("mobile")String mobile,
                                @Query("password")String password
    );

    @GET("rescue/app/guardian/device")
    Observable<JsonDeviceList> getMyDeviceList(
            @Query("userName")String userName,
            @Header("Authorization")String userToken
    );

}
