package com.example.myloginmvvm.ui.login;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.myloginmvvm.MyApplication;
import com.example.myloginmvvm.R;
import com.example.myloginmvvm.ViewModelFactory;
import com.example.myloginmvvm.bean.JsonLogin;
import com.example.myloginmvvm.model.LoginDataSource;
import com.example.myloginmvvm.vm.LoginViewModel;


/**
 * 登录页面
 */
public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
     EditText usernameEditText;
     EditText passwordEditText;
     Button loginButton;
     ProgressBar loadingProgressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        loginViewModel = ViewModelProviders.of(this, new ViewModelFactory((MyApplication) getApplication())).get(LoginViewModel.class);
        /*
        让LoginDataSource能感知Activity的生命周期，在Activity.onstop的时候，LoginDataSource中的某些函数会自动执行,
        这里用到的正是Android JetPack架构组件中的 “LifeCycle”.
        */
        getLifecycle().addObserver(LoginDataSource.getSigleInstance());

        //使用Android JetPack架构组件中的 “liveData”监听数据的变化，一旦有变化则让UI显示这些数据——观察者模式。
        initLiveDataObersve();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 初始化UI控件
     */
    private void initView() {
        usernameEditText = findViewById(R.id.username);
        usernameEditText.setText("18392086025");
        passwordEditText = findViewById(R.id.password);
        passwordEditText.setText("csdn3412");
        loginButton = findViewById(R.id.login);
        loginButton.setEnabled(true);
        loadingProgressBar = findViewById(R.id.loading);

        //去登录
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                //VM层去调遣Model层（Repository+DataSource）获取数据
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }
    private void initLiveDataObersve() {
        //观察服务器返回的数据
        loginViewModel.getJsonLogin().observe(this, new Observer<JsonLogin>() {
            @Override
            public void onChanged(JsonLogin jsonLogin) {
                loadingProgressBar.setVisibility(View.GONE);
                int status = jsonLogin.getStatus();
                if (status == 1) {
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    i.putExtra("userName",jsonLogin.getData().getMobile());
                    startActivity(i);
                    Toast.makeText(LoginActivity.this, "登录成功",Toast.LENGTH_SHORT).show();
                } else {
                    String msg;
                    if(status == -1) { //通信异常
                         msg =  jsonLogin.getThrowable().getMessage();
                    }
                    else {//业务上的失败
                         msg = jsonLogin.getMsg();
                    }
                    Toast.makeText(LoginActivity.this, "登录失败"+msg, Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}