package com.pyar.newsapp.databse;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by RAVI on 7/24/2017.
 */

public class SharedPref {


    //Setting whether this app installed /opened for the first time or not
    public static void setSharedPref(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("IsAlreadyInstalled", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("isFirstTime",false);
        editor.commit();
    }

    //getting status of the app whether this app installed /opened for the first time or not
    public static boolean getSharedPref(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("IsAlreadyInstalled", Context.MODE_PRIVATE);
        boolean status= sharedpreferences.getBoolean("isFirstTime",true);
        return status;
    }

}
