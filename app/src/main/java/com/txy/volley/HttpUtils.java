package com.txy.volley;

import java.util.Map;
import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.txy.constants.Constants;

/**
 * Created by Administrator on 2015/8/6.
 */
public class HttpUtils {
    private static RequestQueue mRequestQueue;

    private HttpUtils() {
    }

    private static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static void post(Context context, String url, final Map<String, String> params,
                            final VolleyListener listener) {
        StringRequest myReq = new UTFStringRequest(Method.POST, url,
                new Listener<String>() {
                    public void onResponse(String response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return params;
            }
        };
        if (mRequestQueue == null) {
            init(context);
        }

        //请用缓存
        myReq.setShouldCache(true);

        //设置缓存时间10分钟
//		myReq.setCacheTime(10*60);
        mRequestQueue.add(myReq);
    }

    public static void get(Context context, String url,
                           final VolleyListener listener) {
        StringRequest myReq = new UTFStringRequest(Method.GET, url,
                new Listener<String>() {
                    public void onResponse(String response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        });
        if (mRequestQueue == null) {
            init(context);
        }
        // 设置延时时间
        myReq.setRetryPolicy(new DefaultRetryPolicy(Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(myReq);
    }

    public static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

}
