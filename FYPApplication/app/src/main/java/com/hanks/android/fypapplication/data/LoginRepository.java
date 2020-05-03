package com.hanks.android.fypapplication.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hanks.android.fypapplication.data.model.LoggedInUser;
import com.hanks.android.fypapplication.utils.Callback;
import com.hanks.android.fypapplication.utils.VolleyCallback;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    private Application context;

    public final String LOGIN_PREFS = "FYPApplication";

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource, Application context) {
        this.dataSource = dataSource;
        this.context = context;
    }

    public LoggedInUser getCurrentUser() {
        return user;
    }

    public static synchronized LoginRepository getInstance(LoginDataSource dataSource, Application context) {
        if (instance == null) {
            instance = new LoginRepository(dataSource, context);
        }
        return instance;
    }

    public void isLoggedIn(Callback isLoggedInCallback) {
        SharedPreferences prefs = context.getSharedPreferences(LOGIN_PREFS, Context.MODE_PRIVATE);
        String username = prefs.getString("username", "notfound");
        String password = prefs.getString("password", "notfound");
        if (username.equals("notfound") || password.equals("notfound")) {
            isLoggedInCallback.onFailure();
        } else {
            login(username, password, new VolleyCallback() {
                @Override
                public void onSuccess(Object o) {
                    isLoggedInCallback.onSuccess();
                }

                @Override
                public void onError(Result e) {
                    isLoggedInCallback.onFailure();
                }
            });
            }
        }

    public void logout() {
        user = null;
        SharedPreferences.Editor editor = context.getSharedPreferences(LOGIN_PREFS, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user, String username, String password) {
        this.user = user;
        SharedPreferences.Editor editor = context.getSharedPreferences(LOGIN_PREFS, Context.MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public void login(String username, String password, VolleyCallback loginCallback) {
        // handle login
        Log.i(LoginRepository.class.getName(), "Trying username: " + username + " and password: " + password);
        dataSource.login(username, password, new VolleyCallback() {
            @Override
            public void onSuccess(Object o) {
                    setLoggedInUser(((Result.Success<LoggedInUser>) o).getData(), username, password);
                    loginCallback.onSuccess(o);
            }

            @Override
            public void onError(Result error) {
                    loginCallback.onError(error);
            }
        });
    }
}
