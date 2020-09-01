package com.example.myloginmvvm;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import com.example.myloginmvvm.model.HomeDataSource;
import com.example.myloginmvvm.model.HomeRepository;
import com.example.myloginmvvm.model.LoginDataSource;
import com.example.myloginmvvm.model.LoginRepository;
import com.example.myloginmvvm.vm.HomeViewModel;
import com.example.myloginmvvm.vm.LoginViewModel;

/**
 这个工厂用来 生成ViewModel（VM层的实例）
 其中Repository+DataSource共同构成了M层。
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    MyApplication mApp;
    public ViewModelFactory(MyApplication app)
    {
        mApp = app;
    }
    public ViewModelFactory()
    {
    }

    /**
     *生成各页面的ViewModel
     * @param modelClass:VM层的类型，本Demo为LoginViewModel和HomeViewModel将会代替<T>
     * @param <T> 类泛型
     * @return ViewModel
     */
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {

            //生成VM层实例，其中HomeRepository+HomeDataSource共同构成了M层。
            return (T) new LoginViewModel(mApp,LoginRepository.getInstance(LoginDataSource.getSingleInstance()));
        }
        else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            //生成VM层实例，其中HomeRepository+HomeDataSource共同构成了M层。
            return (T) new HomeViewModel(HomeRepository.getInstance( HomeDataSource.getSingleInstance()));
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}