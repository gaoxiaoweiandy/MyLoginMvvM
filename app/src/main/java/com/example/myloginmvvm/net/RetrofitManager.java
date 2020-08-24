package com.example.myloginmvvm.net;
import android.os.Environment;
import com.example.myloginmvvm.bean.MyConstant;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 初始化okhttp,retrofit,获取各模块的RetrofitApiService（包含了所有要调用的后台接口的类）
 * 单例模式，只有一个RetrofitManager实例，只有一个okhttpClient实例，只有一个retrofit实例。
 *
 */
public class RetrofitManager {
    private static RetrofitManager retrofitManager;
    private OkHttpClient okHttpClient;
    static private Retrofit retrofit;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    static private RetrofitApiService retrofitApiService;

    /**
     * 获取RetrofitManager单例
     * @return
     */
    public static RetrofitManager getRetrofitManager() {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitManager == null) {
                    retrofitManager = new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }

    /**
     * 构造函数
     */
    private RetrofitManager() {
        //初始化并生成一个okhttpClient实例
        initOkHttpClient();
        //初始化并生成一个Retrofit实例
        initRetrofit();
    }
    /**
     * 获取Service类：包含了所有后台接口
     * @return
     */
    public static RetrofitApiService getApiService() {
        if (retrofitManager == null) {
            retrofitManager = getRetrofitManager();
        }
        retrofitApiService = retrofit.create(RetrofitApiService.class);
        return retrofitApiService;
    }

    /**
     * 初始化okthttpClient实例
     */
    private void initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                //设置缓存文件路径，和文件大小
                .cache(new Cache(new File(Environment.getExternalStorageDirectory() + "/okhttp_cache/"), 50 * 1024 * 1024))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                //.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .addHeader("Accept-Encoding", "gzip, deflate")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                /*  .addHeader("tokenId", "gaoxiaowei")*/
                                .build();
                        return chain.proceed(request);
                    }
                });
        okHttpClient = builder.build();
    }

    /**
     * 初始化并生成一个Retrofit实例
     */
    private void initRetrofit() {
        String baseUrl;

        if (MyConstant.BUILD_TYPE.equals("debug")) {
            baseUrl = MyConstant.ROOT_DEBUG;
        } else {
            baseUrl = MyConstant.ROOT_RELEASE;
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .client(okHttpClient)
                .build();
    }
}
