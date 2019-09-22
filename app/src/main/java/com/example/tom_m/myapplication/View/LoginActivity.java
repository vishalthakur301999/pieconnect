package com.example.tom_m.myapplication.View;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.tom_m.myapplication.Control.ILoginView;
import com.example.tom_m.myapplication.Control.LoginPresenter;
import com.example.tom_m.myapplication.Control.PersistData;
import com.example.tom_m.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    private FrameLayout frameLayout;

    @BindView(R.id.password) EditText password;
    @BindView(R.id.hostname) EditText hostname;
    @BindView(R.id.username) EditText username;
    @BindView(R.id.port)     EditText port;

    LoginPresenter presenter;
    ProgressDialog progressDialog;
    public final static String TAG = "LoginActivity";
    PersistData persistData = new PersistData(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        frameLayout = findViewById(R.id.progress_view);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this);
        loadData();
    }

    private void loadData(){
        hostname.setText(persistData.getStr("input_host"));
        username.setText(persistData.getStr("input_user"));
        password.setText(persistData.getStr("input_password"));
        port.setText(String.valueOf(persistData.getInt("input_port")));
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

    @OnClick(R.id.connect)
    public void connectClicked(View view) {
        Log.i(TAG, "Connect button clicked");
        if (    isEditTextEmpty(hostname)   || isEditTextEmpty(password) ||
                isEditTextEmpty(port)       || isEditTextEmpty(username)) {
            Toast.makeText(this, "Information missing!", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"Information missing");
        } else {
            Log.e(TAG,"Starting login");
            progressDialog = ProgressDialog.show(this, "Authenticating", null);
            presenter.attemptLogin(hostname.getText().toString(), username.getText().toString(), password.getText().toString(), Integer.parseInt(port.getText().toString()));
        }
    }

    @Override
    public void navigateToMainActivity(){
        progressDialog.dismiss();
        Toast.makeText(this, "Login Sucess!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void saveData(String host, String user, String password, int port){
        persistData.setStr("input_host", host);
        persistData.setStr("input_user", user);
        persistData.setStr("input_password", password);
        persistData.setInt("input_port", port);
    }

    @Override
    public void loginFailed(){
        progressDialog.dismiss();
        Toast.makeText(this, "Login Invalid", Toast.LENGTH_SHORT).show();
    }

    /**
     * Checks if EditText is empty
     * @param editText
     * @return true if empty
     */
    private boolean isEditTextEmpty(EditText editText) {
        if (editText.getText() == null || editText.getText().toString().equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }
}