package com.hanks.android.fypapplication.utils;

import com.android.volley.VolleyError;
import com.hanks.android.fypapplication.data.Result;

public interface VolleyCallback {
    public void onSuccess(Object o);
    public void onError(Result error);
}
