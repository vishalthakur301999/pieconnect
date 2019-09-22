package com.example.tom_m.myapplication.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.tom_m.myapplication.Model.ServerInstance;
import com.example.tom_m.myapplication.R;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "Remote Server Access";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    @OnClick(R.id.adduser)
    public void adduserClicked(View view) {
        Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.disableuser)
    public void disableduserClicked(View view) {
        Intent intent = new Intent(MainActivity.this, DisableUserActivity.class);
        startActivity(intent);
    }
    public void deleteuserClicked(View view) {
        Intent intent = new Intent(MainActivity.this, DeleteUserClass.class);
        startActivity(intent);
    }
    public void changepassClicked(View view) {
        Intent intent = new Intent(MainActivity.this, ChangePassClass.class);
        startActivity(intent);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.command_execution) {
            openCommandExecutionActivity();
        } else if (id == R.id.server_information) {
            openServerInformationActivity();
        } else if (id == R.id.macros) {
            openMacroActivity();
        } else if (id == R.id.iplocation) {
            openIPLocation();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Opens CommandExecutionActivity
     */
    public void openServerInformationActivity(){
        Intent intent = new Intent(this, ServerInformationActivity.class);
        startActivity(intent);
    }

    /**
     * Opens CommandExecutionActivity
     */
    public void openCommandExecutionActivity(){
        Intent intent = new Intent(this, CommandExecution.class);
        startActivity(intent);
    }

    /**
     * Opens MacroViewActivity
     */
    public void openMacroActivity(){
        Intent intent = new Intent(this, MacroViewActivity.class);
        startActivity(intent);
    }

    /**
     * Opens IPLocationActivity
     */
    public void openIPLocation(){
        Intent intent = new Intent(this, IPLocationActivity.class);
        startActivity(intent);
    }

}
