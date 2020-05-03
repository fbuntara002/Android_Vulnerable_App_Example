package com.hanks.android.fypapplication.ui.login;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import android.util.Log;
import android.util.Patterns;

import com.hanks.android.fypapplication.data.LoginDataSource;
import com.hanks.android.fypapplication.data.LoginRepository;
import com.hanks.android.fypapplication.data.Result;
import com.hanks.android.fypapplication.data.model.LoggedInUser;
import com.hanks.android.fypapplication.R;
import com.hanks.android.fypapplication.utils.VolleyCallback;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;


    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        loginRepository.login(username, password, new VolleyCallback() {
            @Override
            public void onSuccess(Object o) {
                LoggedInUser data = ((Result.Success<LoggedInUser>) o).getData();
                loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
            }

            @Override
            public void onError(Result error) {
                Exception exception = ((Result.Error) error).getError();
                if (exception.getMessage().contains(LoginDataSource.AUTHENTICATION_FAILED)) {
                    loginResult.setValue(new LoginResult(R.string.login_failed_authentication));
                } else {
                    loginResult.setValue(new LoginResult(R.string.login_failed_unknown));
                }
            }
        });
        }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.length() != 0;
    }
}
