package com.example.tom_m.myapplication.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tom_m.myapplication.Model.Macro;
import com.example.tom_m.myapplication.R;

public class SingleMacroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_macro);

        Intent intent = getIntent();
        Macro exampleItem = intent.getParcelableExtra("Example Item");

        int imageRes            = exampleItem.getImageResource();
        String title            = exampleItem.getTitle();
        String command          = exampleItem.getCommand();
        String lastExecution    = exampleItem.getLastExecution();
        int executed            = exampleItem.getAmountsExecuted();
        int exitCode            = exampleItem.getExitCode();

        ImageView imageView = findViewById(R.id.icon);
        imageView.setImageResource(imageRes);

        TextView textViewTitle = findViewById(R.id.title);
        textViewTitle.setText(title);

        TextView textViewCommand = findViewById(R.id.command);
        textViewCommand.setText(command);

        TextView textViewlastExecuted = findViewById(R.id.lastExecution);
        textViewlastExecuted.setText(lastExecution);

        TextView textViewExecuted = findViewById(R.id.amountsOfExecution);
        textViewExecuted.setText(Integer.toString(executed));

        TextView textViewExitCode = findViewById(R.id.exitCode);
        textViewExitCode.setText(Integer.toString(exitCode));

    }
}
