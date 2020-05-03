package com.hanks.android.fypapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.hanks.android.fypapplication.data.LoginDataSource;
import com.hanks.android.fypapplication.data.LoginRepository;
import com.hanks.android.fypapplication.main.MainActivity;
import com.hanks.android.fypapplication.ui.login.LoginActivity;
import com.hanks.android.fypapplication.utils.Callback;

public class SplashActivity extends AppCompatActivity {

    LoginRepository loginRepository;

    Application context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this.getApplication();
        loginRepository = LoginRepository.getInstance(new LoginDataSource(context), context);

        loginRepository.isLoggedIn(new Callback() {
            @Override
            public void onSuccess() {
                Intent activityIntent = new Intent(context, MainActivity.class);
                startActivity(activityIntent);
                finish();
            }

            @Override
            public void onFailure() {
                Intent activityIntent = new Intent(context, LoginActivity.class);
                startActivity(activityIntent);
                finish();
            }
        });
    }
}