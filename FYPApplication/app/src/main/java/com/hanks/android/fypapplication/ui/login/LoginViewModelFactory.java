package com.hanks.android.fypapplication.ui.login;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.hanks.android.fypapplication.data.LoginDataSource;
import com.hanks.android.fypapplication.data.LoginRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final Application context;

    public LoginViewModelFactory(@NonNull Application context) {
        this.context = context;
    }
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(LoginRepository.getInstance(new LoginDataSource(context), context));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
