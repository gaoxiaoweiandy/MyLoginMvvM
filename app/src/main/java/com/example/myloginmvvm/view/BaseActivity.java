package com.example.myloginmvvm.view;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import com.example.myloginmvvm.MyApplication;
import com.example.myloginmvvm.R;
import com.example.myloginmvvm.ViewModelFactory;
import com.example.myloginmvvm.bean.Result;
import com.example.myloginmvvm.net.NetWorkUtils;
import com.example.myloginmvvm.view.selfview.CustomProgress;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

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
    private CustomProgress dialog;
    public abstract class OnCallback<T> implements Result.OnHandleCallback<T> {
        @Override
        public void onLoading(String msg) {
            if (dialog == null) {
                dialog = CustomProgress.show(BaseActivity.this, "", true, null);
            }

            if (!TextUtils.isEmpty(msg)) {
                dialog.setMessage(msg);
            }

            if (!dialog.isShowing()) {
                dialog.show();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            if (!NetWorkUtils.isNetworkConnected(getApplicationContext())) {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.result_network_error),Toast.LENGTH_SHORT).show();
                return;
            }
            if (throwable instanceof ConnectException) {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.result_server_error),Toast.LENGTH_SHORT).show();
            } else if (throwable instanceof SocketTimeoutException) {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.result_server_timeout),Toast.LENGTH_SHORT).show();
            } else if (throwable instanceof JsonSyntaxException) {
                Toast.makeText(getApplicationContext(),"数据解析出错",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.result_empty_error),Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(String msg) {
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCompleted() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        public void onProgress(int precent, long total) {

        }
    }

}