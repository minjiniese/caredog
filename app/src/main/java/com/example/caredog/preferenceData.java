package com.example.caredog;

import android.content.Context;
import android.content.SharedPreferences;

public class preferenceData {
    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("pref",context.MODE_PRIVATE);
    }

    public static void setJ(Context context,String key, int value){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getJ(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        int value = prefs.getInt(key, 0);
        return value;
    }

    //string 추가
    public static void setString(Context context,String key, String value) {
        SharedPreferences pref = getPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);

        editor.commit();
    }

    //string 로드
    public static String getString(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key,"");
        return value;
    }

    //string 제거
    public static void removeKey(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove(key);
        edit.commit();
    }
}
