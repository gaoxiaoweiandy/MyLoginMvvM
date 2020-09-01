package com.example.myloginmvvm.net;
import com.example.myloginmvvm.bean.JsonDataCommon;
import com.example.myloginmvvm.bean.JsonDeviceListData;
import com.example.myloginmvvm.bean.JsonLoginData;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 *  所有服务器接口函数，当然 这里也可以分解成模块，例如为登录创建一个RetrofitLoginApiService，再为首页
 *  专门创建一个RetrofitHomeApiService
 */
public interface RetrofitApiService {

    /**
     * 登录
     * @param mobile： 手机号作为用户名
     * @param password： 密码
     * @return
     */
    @POST("authorization/guardian/login")
    Observable<JsonDataCommon<JsonLoginData>> login(
                                @Query("mobile")String mobile,
                                @Query("password")String password
    );

    /**
     * 获取首页列表数据
     * @param userName： 用户名即为mobile
     * @param userToken: 登录时返回的token
     * @return
     */
    @GET("rescue/app/guardian/device")
    Observable<JsonDataCommon<JsonDeviceListData>> getMyDeviceList(
            @Query("userName")String userName,
            @Header("Authorization")String userToken
    );

}
