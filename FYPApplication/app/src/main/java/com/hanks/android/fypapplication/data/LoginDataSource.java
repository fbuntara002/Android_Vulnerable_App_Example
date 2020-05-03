package com.hanks.android.fypapplication.data;

import android.app.Application;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hanks.android.fypapplication.R;
import com.hanks.android.fypapplication.data.model.LoggedInUser;
import com.hanks.android.fypapplication.utils.VolleyCallback;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public static final String AUTHENTICATION_FAILED = "Invalid username or password";
    public static final String CONNECTION_TIMEOUT = "Connection timeout, please make sure the internet connection";
    public static final String BAD_REQUEST = "Bad request, please check output";
    public static final String UNKNOWN_ERROR = "Unknown Error";


    private final String loginurl = "http://192.168.0.101:3000/api/login";

    private Application context;

    //Mock for restFUL API
//    private final Map<String, String> userAuth = createUserAuth();
    private final Map<String, String> userDisplay = createUserDisplay();
    private final Map<String, Integer> userProfile = createUserProfilePicture();

//    private Map<String, String> createUserAuth() {
//        Map<String, String> userAuth = new HashMap<>();
//        userAuth.put("fuhankb", "Buntara");
//        userAuth.put("chens", "Sen");
//        return userAuth;
//    }

    private Map<String, String> createUserDisplay() {
        Map<String, String> userDisplay = new HashMap<>();
        userDisplay.put("fuhankb", "Fuhank Buntara");
        userDisplay.put("chens", "Chen Sen");
        return userDisplay;
    }

    private Map<String, Integer> createUserProfilePicture() {
        Map<String, Integer> userProfile = new HashMap<>();
        userProfile.put("fuhankb", R.drawable.jotaro);
        userProfile.put("chens", R.drawable.shibainu);
        return userProfile;
    }

    public LoginDataSource(Application context) {
        this.context = context;
    }

    public void login(String username, String password, VolleyCallback loginCallback) {
        try {
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, loginurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    LoggedInUser authenticatedUser =
                            new LoggedInUser(
                                    username,
                                    userDisplay.get(username),
                                    userProfile.get(username));
                    loginCallback.onSuccess(new Result.Success<>(authenticatedUser));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    if (error instanceof TimeoutError) {
                        loginCallback.onError(new Result.Error(new LoginException(CONNECTION_TIMEOUT)));
                    } else if (error instanceof AuthFailureError) {
                        loginCallback.onError(new Result.Error(new LoginException(AUTHENTICATION_FAILED)));
                    } else if (error instanceof ParseError) {
                        loginCallback.onError(new Result.Error(new LoginException(BAD_REQUEST)));
                    } else {
                        loginCallback.onError(new Result.Error(new LoginException(UNKNOWN_ERROR)));
                    }
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };
            queue.add(stringRequest);
        } catch (Exception e) {
            loginCallback.onError(new Result.Error(e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
