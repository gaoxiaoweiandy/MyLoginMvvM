package com.example.myloginmvvm;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.myloginmvvm.model.login.LoginDataSource;
import com.example.myloginmvvm.model.login.LoginRepository;
import com.example.myloginmvvm.vm.HomeViewModel;
import com.example.myloginmvvm.vm.LoginViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(LoginRepository.getInstance(LoginDataSource.getSigleInstance()));
        }
        else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            //这里也可将LoginDataSource替换为HomeDataSource，同login区分开数据来源，也就是说HomeDataSource
            // 只负责为首页提供数据
            return (T) new HomeViewModel(LoginRepository.getInstance(new LoginDataSource()));
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}