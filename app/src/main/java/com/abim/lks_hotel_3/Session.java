package com.abim.lks_hotel_3;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    SharedPreferences prefs;

    public Session(Context ctx) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public void  setUser(int id, String name){
        prefs.edit().putInt("id", id).commit();
        prefs.edit().putString("name", name).commit();
    }

    public int getId(){
        return prefs.getInt("id", 0);
    }

    public  String getName(){
        return prefs.getString("name", "");
    }
}
