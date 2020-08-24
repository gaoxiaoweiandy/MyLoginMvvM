package com.example.myloginmvvm.vm;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.app.Application;
import android.util.Log;
import com.example.myloginmvvm.MyApplication;
import com.example.myloginmvvm.bean.JsonLogin;
import com.example.myloginmvvm.model.LoginRepository;
/**
 * 登录页面的VM层，调用M层去获取数据，同时更改liveData的数据，从而View层（LoginActivity)就能观察到LiveData数据的
 * 变化，最后更新UI。
 */
public class LoginViewModel extends AndroidViewModel {

    String TAG = "AACLoginViewModel";
    private MutableLiveData<JsonLogin> jsonLoginLiveData = new MutableLiveData<>();
    private LoginRepository loginRepository; //M层引用
    private MyApplication mApp;

    /**
     * 构造函数
     * @param app：为了防止内存泄露，传递一个Application作为context
     * @param loginRepository: M层对象实例
     */
    public LoginViewModel(Application app,LoginRepository loginRepository) {
        super(app);
        this.mApp = getApplication();
        this.loginRepository = loginRepository;
    }
    public MutableLiveData<JsonLogin> getJsonLogin() {
        return jsonLoginLiveData;
    }
    /**
     * VM层调用M层（LoginRepository+DataSource）
     * @param username： 用户名
     * @param password： 密码
     * @return
     */
    public LiveData<JsonLogin> login(String username, String password) {
        jsonLoginLiveData = loginRepository.login(username, password,jsonLoginLiveData,mApp);
        Log.i(TAG,"login");
        return jsonLoginLiveData;
    }
}