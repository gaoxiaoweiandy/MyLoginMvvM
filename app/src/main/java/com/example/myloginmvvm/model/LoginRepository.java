package com.example.myloginmvvm.model;
import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import com.example.myloginmvvm.bean.JsonLogin;
/**
 * 可以将Repository和 DataSource这两者合并起来看作是MVVM中的Model层，实质最终获取服务起数据的是DataSource,
 * 这里引入Repository的目的是根据业务需求提前对服务器返回的数据进行加工处理（最终接近View层需要显示的数据结构），这样VM(ViewModel)层将会变得简洁，而DataSource
 * 只负责从服务器获取数据，达到获取数据模块化。
 */
public class LoginRepository {
    private static volatile LoginRepository instance;
    private LoginDataSource dataSource; //专门用于负责从服务器接口获取数据
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取单例
     * @return
     */
    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }


    /**
     * 调遣dataSource去调用服务器接口，
     * @param username： 用户名
     * @param password：密码
     * @param jsonLoginLiveData： 服务器返回的数据 填充进LiveData
     * @param app：将Myapplication作为全局context,以防止内存泄露;切记不要传递Activity的this作为上下文。
     * @return
     */
    public  MutableLiveData<JsonLogin> login(String username, String password, MutableLiveData<JsonLogin> jsonLoginLiveData, Application app) {
        MutableLiveData<JsonLogin> result = dataSource.login(username, password,jsonLoginLiveData, app);
        return result;
    }
}