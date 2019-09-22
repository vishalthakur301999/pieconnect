package com.example.tom_m.myapplication.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import com.example.tom_m.myapplication.Control.PersistData;
import com.example.tom_m.myapplication.Model.ServerInstance;
import com.example.tom_m.myapplication.R;
import com.example.tom_m.myapplication.SSHDaemon.SSHExecuteCommand;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommandExecution extends AppCompatActivity {

    public static final String TAG = "CommandExecution";
    PersistData persistData = new PersistData(this);
    SSHExecuteCommand exec = new SSHExecuteCommand();

    @BindView(R.id.command)     EditText command;
    @BindView(R.id.output)      TextView output;
    @BindView(R.id.hostname)    TextView hostname;
    @BindView(R.id.user)        TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_execution);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        output.setMovementMethod(new ScrollingMovementMethod());
        loadData();
    }

    private void loadData(){
        hostname.setText(persistData.getStr("input_host") + ":" + String.valueOf(persistData.getInt("input_port")));
        user.setText(persistData.getStr("input_user"));
        output.setText(persistData.getStr("command_output"));
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

    @OnClick(R.id.submit)
    public void connectClicked(View view) {
        Log.i(TAG, "submit button clicked");
        String commandOutput = exec.startConnection(ServerInstance.getInstance(), command.getText().toString());
        String shellOutput = getPS() + commandOutput;
        output.append(shellOutput);
        persistData.setStr("command_output", shellOutput);
    }

    @OnClick(R.id.increaseTextsize)
    public void increaseTextsizeClicked(View v){
        float currentTextsize = output.getTextSize();
        float newTextsize = currentTextsize+1;
        Log.i(TAG, "increasing textsize from " + currentTextsize + " to " + newTextsize);
        output.setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextsize);
    }

    @OnClick(R.id.decreaseTextsize)
    public void decreaseTextsizeClicked(View v){
        float currentTextsize = output.getTextSize();
        float newTextsize = currentTextsize-1;
        Log.i(TAG, "decreasing textsize from " + currentTextsize + " to " + newTextsize);
        output.setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextsize);
    }

    @OnClick(R.id.clear)
    public void clearClicked(View v){
        output.setText(getPS()+"\n");
    }


    private String getPS(){
        return persistData.getStr("input_user") + "@" + persistData.getStr("input_host") + ":~$ ";
    }

}
