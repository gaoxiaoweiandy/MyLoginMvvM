package com.example.myloginmvvm.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.myloginmvvm.MyApplication;
import com.example.myloginmvvm.R;
import com.example.myloginmvvm.ViewModelFactory;
import com.example.myloginmvvm.bean.JsonLogin;
import com.example.myloginmvvm.bean.TwoDirectionBindingSimple;
import com.example.myloginmvvm.databinding.ActivityLoginBinding;
import com.example.myloginmvvm.model.LoginDataSource;
import com.example.myloginmvvm.vm.HomeViewModel;
import com.example.myloginmvvm.vm.LoginViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Activity基类，在各子类Activity里需要重复写的代码，统一都放在这里，通过继承此基类重用这些代码
 */
public abstract class BaseActivity<VM extends ViewModel,VDB extends ViewDataBinding> extends AppCompatActivity {
    VM mViewModel;
    VDB mDataBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //实例化布局文件对象(dataBindingLogin)
        mDataBinding = DataBindingUtil.setContentView(this,getContentViewId());
        //创建每一个Activity对应的ViewModel
        createViewModel();
        addLifeCycleObserver();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 根据泛型类的第一个参数VM传递过来的具体类型，new出这个具体类型的viewModel
     * 如new出 loginViewModel 与 HomeViewModel
     */
    public void createViewModel()
    {
        if(mViewModel == null)
        {
            Class myViewModelClass;
            Type type = getClass().getGenericSuperclass();
            if(type instanceof ParameterizedType) {
                myViewModelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            }
            else {
                myViewModelClass = AndroidViewModel.class;
            }
            mViewModel = (VM)ViewModelProviders.of(this, new ViewModelFactory((MyApplication) getApplication())).get(myViewModelClass);
        }
        return;
    }

    abstract int getContentViewId();
    protected abstract void addLifeCycleObserver();
}