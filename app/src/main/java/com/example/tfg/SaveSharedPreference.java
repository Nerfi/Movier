package com.example.tfg;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_USER_NAME = "username";
    static final int PREF_USER_UID = 0;// check this in case I run into problem

    static SharedPreferences getSharedPreferences(Context ctx) {
       // return PreferenceManager.getPreferences(Context.MODE_PRIVATE);
      // return SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return ctx.getApplicationContext().getSharedPreferences("userLogged", 0); // 0 - for private mode

    }
    public static void setUserName(Context ctx, String userName){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME,userName);
        editor.commit();
    }
    //getters
    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void setUserUid(Context ctx, int userUid){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt("userUid", userUid);
        editor.commit(); // maybe use apply();
    }
    //getter
    public static int getUserUid(Context ctx) {
        return getSharedPreferences(ctx).getInt("userUid",0);
    }

    //log out
    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
}
