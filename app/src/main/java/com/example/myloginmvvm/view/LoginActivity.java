package com.example.myloginmvvm.view;
import androidx.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.myloginmvvm.R;
import com.example.myloginmvvm.bean.JsonLoginData;
import com.example.myloginmvvm.bean.Result;
import com.example.myloginmvvm.bean.TwoDirectionBindingSimple;
import com.example.myloginmvvm.databinding.ActivityLoginBinding;
import com.example.myloginmvvm.model.LoginDataSource;
import com.example.myloginmvvm.vm.LoginViewModel;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity<LoginViewModel,ActivityLoginBinding> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        //使用Android JetPack架构组件中的 “liveData”监听数据的变化，一旦有变化则让UI显示这些数据——观察者模式。
        initLiveDataObersve();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void addLifeCycleObserver() {
        /*
        让LoginDataSource能感知Activity的生命周期，在Activity.onstop的时候，LoginDataSource中的某些函数会自动执行,
        这里用到的正是Android JetPack架构组件中的 “LifeCycle”.
        */
        getLifecycle().addObserver(LoginDataSource.getSingleInstance());
    }


    /**
     * 初始化UI控件
     * 使用布局文件对象dataBindingLogin，可以告别FindViewById了.
     */
    private void initView() {
        mDataBinding.login.setEnabled(true);

        /*
        //单向绑定举例，从变量到UI
        User user = new User();
        user.setUserPhone("18392086025");
        user.setPassword("csdn3412");
        dataBindingLogin.setUser(user);
       */
        /*
        //双向绑定
        原始版（BaseObservable）
        TwoDirectionBinding twoDirectionBinding = new TwoDirectionBinding();
        dataBindingLogin.setTwoDirectionBinding(twoDirectionBinding);*/
        //简化版（ObservableField）
        TwoDirectionBindingSimple twoDirectionBindingSimple = new TwoDirectionBindingSimple();
        mDataBinding.setTwoDirectionBindingSimple(twoDirectionBindingSimple);

        //去登录
        mDataBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VM层去调遣Model层（Repository+DataSource）获取数据
                mViewModel.login(mDataBinding.username.getText().toString(),
                        mDataBinding.password.getText().toString());
            }
        });
    }

    /**
     * liveData观察者监听数据变化，从而更新UI
     */
    private void initLiveDataObersve() {

        mViewModel.getJsonLogin().observe(this, new Observer<Result<JsonLoginData>>() {
            @Override
            public void onChanged(Result<JsonLoginData> result) {

                result.handler(new OnCallback<JsonLoginData>() {
                    @Override
                    public void onSuccess(JsonLoginData data) {
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        i.putExtra("userName",data.getMobile());
                        startActivity(i);
                        Toast.makeText(LoginActivity.this, "登录成功",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}