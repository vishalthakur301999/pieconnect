package com.example.tom_m.myapplication.Control;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PersistData {

    public final static String SHARED_PREFS="AndroidShell";
    public final static String TAG = "PersistData";

    private static Context context;

    public PersistData(Context context){
        this.context = context;
    }

    public static void setInt( String key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS,0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
        Log.i(TAG, "Integer \"" + value + "\" saved to shared_prefs");
    }

    public static int getInt(String key) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, 0);
        int return_value =  prefs.getInt(key, 22);
        Log.i(TAG, "Integer \"" + return_value + "\" returned from shared_prefs");
        return return_value;
    }

    public static void setStr(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS,0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
        Log.i(TAG, "String \"" + value + "\" saved to shared_prefs");
    }

    public static String getStr(String key) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, 0);
        String return_value =  prefs.getString(key,"");
        Log.i(TAG, "String \"" + return_value + "\" returned from shared_prefs");
        return return_value;
    }

    public static void setBool(String key, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS,0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
        Log.i(TAG, "Boolean \"" + value + "\" saved to shared_prefs");
    }

    public static boolean getBool(String key) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, 0);
        boolean return_value = prefs.getBoolean(key,false);
        Log.i(TAG, "Integer \"" + return_value + "\" returned from shared_prefs");
        return return_value;
    }
}
