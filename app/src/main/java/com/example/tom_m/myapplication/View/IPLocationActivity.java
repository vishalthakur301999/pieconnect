package com.example.tom_m.myapplication.View;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tom_m.myapplication.Control.JsonRequest;
import com.example.tom_m.myapplication.Model.ServerInstance;
import com.example.tom_m.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;


public class IPLocationActivity extends FragmentActivity  implements OnMapReadyCallback {

    private static final String BASE_URL = "http://ip-api.com/json/";
    private static final String TAG = "IPLocationActivity";

    private JSONObject jsonObject;
    private RequestQueue requestQueue;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iplocation);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG, TAG + " onStart invoked");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, TAG + " onResume invoked");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG, TAG + " onPause invoked");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG, TAG + " onStop invoked");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i(TAG, TAG + " onRestart invoked");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, TAG + " onDestroy invoked");
    }

    public void onMapReady(GoogleMap googleMap) {
        LatLng ipLocation;
        if (ServerInstance.getInstance().getLat() == null || ServerInstance.getInstance().getLon() == null) {
            ipLocation = new LatLng(0.0,0.0);
            Toast.makeText(this, "Please update ServerInformation", Toast.LENGTH_LONG).show();
        } else {
            ipLocation = new LatLng(ServerInstance.getInstance().getLat(), ServerInstance.getInstance().getLon());
        }
        googleMap.addMarker(new MarkerOptions().position(ipLocation).title("IP Location of " + ServerInstance.getInstance().getHost()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ipLocation));
    }
}
