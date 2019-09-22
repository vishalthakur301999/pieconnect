package com.example.tom_m.myapplication.View;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.tom_m.myapplication.Control.PersistData;
import com.example.tom_m.myapplication.Model.ServerInstance;
import com.example.tom_m.myapplication.R;
import com.example.tom_m.myapplication.SSHDaemon.SSHExecuteCommand;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import android.widget.Toast;


public class DeleteUserClass extends AppCompatActivity {

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
        setContentView(R.layout.delete_user);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        String commandOutput = exec.startConnection(ServerInstance.getInstance(),"cut -d: -f1 /etc/passwd");
        commandOutput = "Select ".concat(commandOutput);
        String[] arr = commandOutput.split("\\s+");
        Spinner spinner = (Spinner) findViewById(R.id.ulist);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, arr);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

    }
    @OnClick(R.id.connect)
    public void connectClicked(View view) {
        Log.i(TAG, "submit button clicked");
        EditText text = (EditText)findViewById(R.id.duname);
        String value = text.getText().toString();
        Spinner spinner = (Spinner) findViewById(R.id.ulist);

        if(spinner.getSelectedItemId()==0){
            if(value.length()==0){
                Toast.makeText(getApplicationContext(),"Please Provide Input",Toast.LENGTH_SHORT).show();
            }
            else{
                String commandOutput = exec.startConnection(ServerInstance.getInstance(),"userdel "+value );
                if(commandOutput.length()==0){
                    Toast.makeText(getApplicationContext(),"Command Successful"+commandOutput,Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(value.length()==0){
            if(spinner.getSelectedItemId()>0){
                String commandOutput = exec.startConnection(ServerInstance.getInstance(),"userdel "+spinner.getSelectedItem().toString());
                if(commandOutput.length()==0){
                    Toast.makeText(getApplicationContext(),"Command Successful"+commandOutput,Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(),"Please Provide Input",Toast.LENGTH_SHORT).show();
            }
        }
    }




    private String getPS(){
        return persistData.getStr("input_user") + "@" + persistData.getStr("input_host") + ":~$ ";
    }

}
