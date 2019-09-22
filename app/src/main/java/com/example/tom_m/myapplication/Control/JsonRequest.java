package com.example.tom_m.myapplication.Control;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class JsonRequest {

    private static JsonRequest jsonRequest;

    private RequestQueue requestQueue;
    private static Context context;

    private JsonRequest(Context context){
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized JsonRequest getInstance(Context context){
        if (jsonRequest == null) jsonRequest = new JsonRequest(context);
        return jsonRequest;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null) requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return requestQueue;
    }

}
