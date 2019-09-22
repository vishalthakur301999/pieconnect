package com.example.tom_m.myapplication.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tom_m.myapplication.Control.MacroAdapter;
import com.example.tom_m.myapplication.Model.Macro;
import com.example.tom_m.myapplication.R;

import java.util.ArrayList;

public class MacroViewActivity extends AppCompatActivity {

    private ArrayList<Macro> macroList;
    private RecyclerView recyclerView;
    private MacroAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macroview);
        createMacroList();
        buildRecyclerView();

    }

    public void createMacroList() {
        macroList = new ArrayList<>();
        macroList.add(new Macro(R.drawable.ic_information,"Add User", "adduser"));
        macroList.add(new Macro(R.drawable.ic_information,"Delete User", "delete user"));
        macroList.add(new Macro(R.drawable.ic_information, "Change user password", "change user password"));
    }

    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new MacroAdapter(macroList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MacroAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MacroViewActivity.this, SingleMacroActivity.class);
                intent.putExtra("Example Item", macroList.get(position));
                startActivity(intent);
            }
        });
    }



}
