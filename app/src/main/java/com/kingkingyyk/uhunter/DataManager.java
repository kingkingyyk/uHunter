package com.kingkingyyk.uhunter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class DataManager {

    public static void saveString (AppCompatActivity act, String key, String str) {
        SharedPreferences pref = act.getSharedPreferences("com.kingkingyyk.uhunter", Context.MODE_PRIVATE);
        if (!pref.getString(key,"").equals(str)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(key, str);
            editor.apply();
        }
    }

    public static String loadString (AppCompatActivity act, String key, String default_value) {
        SharedPreferences pref = act.getSharedPreferences("com.kingkingyyk.uhunter", Context.MODE_PRIVATE);
        return pref.getString(key, default_value);
    }

    public static void clearData (AppCompatActivity act, String key) {
        SharedPreferences pref = act.getSharedPreferences("com.kingkingyyk.uhunter", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.apply();
    }
}
