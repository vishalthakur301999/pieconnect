package com.example.tom_m.myapplication.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tom_m.myapplication.Control.JsonRequest;
import com.example.tom_m.myapplication.Control.PersistData;
import com.example.tom_m.myapplication.R;
import com.example.tom_m.myapplication.SSHDaemon.SSHExecuteCommand;
import com.example.tom_m.myapplication.Model.ServerInstance;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServerInformationActivity extends AppCompatActivity {

    @BindView(R.id.progressBar) ProgressBar progressBar;

    public static final String TAG = "ServerInformationActivity";
    private static final String BASE_URL = "http://ip-api.com/json/";

    PersistData persistData = new PersistData(this);
    SSHExecuteCommand exec = new SSHExecuteCommand();
    private HashMap<Integer, String> attributes = new HashMap<Integer, String>();
    private JSONObject jsonObject;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_information);
        ButterKnife.bind(this);
        requestQueue = JsonRequest.getInstance(this.getApplicationContext()).getRequestQueue();
        initAttributes();
        getIPLocation();
        loadData();
    }

    // inits hashmap with server information items
    private void initAttributes(){
        attributes.put(R.id.userValue, "whoami");
        attributes.put(R.id.ipValue, "for int in $(hostname -I);do  echo -ne $int ,; done");
        attributes.put(R.id.fqdnValue, "hostname --fqdn");
        attributes.put(R.id.osValue, "lsb_release -ds");
        attributes.put(R.id.kernelValue, "uname -r");
        attributes.put(R.id.storageValue, "df -h --output=size --total | sed -n '2p'");
        attributes.put(R.id.uptimeValue, "uptime -p");
        attributes.put(R.id.cpuValue, "grep 'cpu ' /proc/stat | awk '{usage=($2+$4)*100/($2+$4+$5)} END {print usage \"%\"}'");
        attributes.put(R.id.ramValue, "awk '/Total/ {print $2}' <(free -ht)");
    }

    @OnClick(R.id.updateInformation)
    public void updateInformationClicked(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Log.i(TAG, "updateInformation button clicked");
        progressBar.setVisibility(View.VISIBLE);
        loadValues();
        getIPLocation();
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Information updated!", Toast.LENGTH_SHORT).show();
    }

    // executes the commands and places the result to the value labels
    private void loadValues(){
        for(Map.Entry<Integer, String> attribute : attributes.entrySet()) {
                int id = attribute.getKey();
                String command = attribute.getValue();
                updateTextView(id, command);
        }
    }


    private void updateTextView(int textViewId, String command){
        Log.i(TAG, "executing command for " + textViewId + ", " + command);
        String output = exec.startConnection(ServerInstance.getInstance(), command);
        TextView textView = findViewById(textViewId);
        textView.setText(output);
        persistData.setStr(Integer.toString(textViewId), output);
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
        loadData();
        Log.i(TAG, TAG + " onPause invoked");
    }

    @Override
    protected void onStop(){
        super.onStop();
        loadData();
        Log.i(TAG, TAG + " onStop invoked");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        loadData();
        Log.i(TAG, TAG + " onRestart invoked");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, TAG + " onDestroy invoked");
    }

    /**
     * Loads data into attributes
     */
    private void loadData(){
        for(Map.Entry<Integer, String> attribute : attributes.entrySet()) {
            int id = attribute.getKey();
            String command = attribute.getValue();
            TextView textView = findViewById(id);
            textView.setText(persistData.getStr(Integer.toString(id)));
        }
    }

    private void getIPLocation(){
        requestQueue = JsonRequest.getInstance(this.getApplicationContext()).getRequestQueue();
        String url = String.format(BASE_URL + ServerInstance.getInstance().getHost());
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonObject = new JSONObject(response.toString());
                            Log.i(TAG, "response : " + response.toString());

                            Double lat = jsonObject.getDouble("lat");
                            Double lon = jsonObject.getDouble("lon");
                            Log.i(TAG, "lat : " + lat + " lon: " + lon);
                            ServerInstance.getInstance().setLat(lat);
                            ServerInstance.getInstance().setLon(lon);
                        } catch (JSONException e){
                            Log.e(TAG, e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error getting response" + error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
