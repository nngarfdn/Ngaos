package com.udindev.ngaos.ui.auth.base;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.udindev.ngaos.ui.auth.main.viewmodel.LoggedInViewModel;
import com.udindev.ngaos.ui.auth.main.viewmodel.LoginRegisterViewModel;

public class LoggedViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String mParam;


    public LoggedViewModelFactory(Application application) {
        mApplication = application;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new LoggedInViewModel(mApplication);
    }
}