package com.example.tom_m.myapplication.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.tom_m.myapplication.Control.PersistData;
import com.example.tom_m.myapplication.Model.ServerInstance;
import com.example.tom_m.myapplication.R;
import com.example.tom_m.myapplication.SSHDaemon.SSHExecuteCommand;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import android.widget.Toast;


public class AddUserActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
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
        setContentView(R.layout.add_user);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        frameLayout = findViewById(R.id.progress_view);
    }

    @OnClick(R.id.connect)
    public void connectClicked(View view) {
        Log.i(TAG, "submit button clicked");
        EditText text = (EditText)findViewById(R.id.uname);
        String value = text.getText().toString();
        EditText passtext = (EditText)findViewById(R.id.pass);
        String pass = passtext.getText().toString();
        String commandOutput = exec.startConnection(ServerInstance.getInstance(),"useradd "+value );
        String commandOutput2 = exec.startConnection(ServerInstance.getInstance(),"echo '"+value+":"+pass+"' | chpasswd" );
        Toast.makeText(getApplicationContext(),commandOutput+commandOutput2,Toast.LENGTH_SHORT).show();
        if(commandOutput.length()==0 && commandOutput2.length()==0){
            Toast.makeText(getApplicationContext(),"Command Successful",Toast.LENGTH_SHORT).show();
        }
    }




    private String getPS(){
        return persistData.getStr("input_user") + "@" + persistData.getStr("input_host") + ":~$ ";
    }

}
