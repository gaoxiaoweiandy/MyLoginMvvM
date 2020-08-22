package com.example.myloginmvvm.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myloginmvvm.R;
import com.example.myloginmvvm.ViewModelFactory;
import com.example.myloginmvvm.bean.JsonLogin;
import com.example.myloginmvvm.bean.User;
import com.example.myloginmvvm.model.bean.LoginFormState;
import com.example.myloginmvvm.model.LoginDataSource;
import com.example.myloginmvvm.vm.LoginViewModel;


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
        loginViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(LoginViewModel.class);
        getLifecycle().addObserver(LoginDataSource.getSigleInstance());
        initLiveDataObersve();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void saveUserData(String token)
    {
        //保存用户数据在本地SharePreference
        User user = new User();
        ContentValues value = new ContentValues();
        value.put("userToken",token);
        user.saveUserInfo(this,value);
    }

    private void initLiveDataObersve() {
        //观察服务器返回的数据
        loginViewModel.getJsonLogin().observe(this, new Observer<JsonLogin>() {
            @Override
            public void onChanged(JsonLogin jsonLogin) {
                loadingProgressBar.setVisibility(View.GONE);
                int status = jsonLogin.getStatus();
                if (status == 1) {
                    saveUserData(jsonLogin.getData().getToken());
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    i.putExtra("userName",jsonLogin.getData().getMobile());
                    startActivity(i);
                    Toast.makeText(LoginActivity.this, "登录成功",Toast.LENGTH_SHORT).show();
                } else {
                    if(status == -1)
                    {

                        String msg =  jsonLogin.getThrowable().getMessage();
                        Toast.makeText(LoginActivity.this, "登录失败"+msg, Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "登录失败" + jsonLogin.getMsg(), Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    private void initView() {
        usernameEditText = findViewById(R.id.username);
        usernameEditText.setText("13072925248");
        passwordEditText = findViewById(R.id.password);
        passwordEditText.setText("csdn3412");
        loginButton = findViewById(R.id.login);
        loginButton.setEnabled(true);
        loadingProgressBar = findViewById(R.id.loading);
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

}