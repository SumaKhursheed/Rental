package com.innowi.appburnin.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import com.innowi.appburnin.App.App;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_WORLD_READABLE;

/**
 * Created by Navdeep on 10/9/2017.
 */

public class SharedAppData {
    private static int testCount = 0;
    private static final String test_count_name = "TestCountFile";
    private static final String RESULT_COLOR_FILE = "resultColorFile";
    private static final String DEFAULT_FILE = "default_preferences";
    private static final String INSTALL_CHECKER = "install_Checker";
    private static final String NEXT_ENABLER = "enable_next_item";
    private static final String BURN_IN_CHECK = "burn_in_check";
    private static boolean burn_in_status = false;


    //Set this file the first time the app is installed

//    public static void setFirstInstall(){
//        SharedPreferences.Editor editor = App.getMyContext()
//                .getSharedPreferences(INSTALL_CHECKER, Context.MODE_PRIVATE).edit();
//        Log.d("=========","SETTING DEFAULT INSTALL");
//        editor.putInt("install_check", );
//        editor.apply();
//    }
//    //check for it on Every creation
//    //0 is default, 1= pass, 2 = fail
//    public static void setDefaultPreferences(){
//        SharedPreferences.Editor editor = App.getMyContext()
//                .getSharedPreferences(DEFAULT_FILE, Context.MODE_PRIVATE).edit();
//        Log.d("=========","SETTING DEFAULT PREFERENCES");
//        editor.putInt("display",0);
//        editor.putInt("audio",0);
//        editor.putInt("touch",0);
//        editor.putInt("cpu",0);
//        editor.putInt("continuity",0);
//        editor.putInt("wifi",0);
//        editor.putInt("scanner",0);
//        editor.putInt("bluetooth",0);
//        editor.putInt("brightness",0);
//        editor.putInt("storage",0);
//        editor.putInt("sensors",0);
//        editor.putInt("gps",0);
//        editor.putInt("applications",0);
//        editor.putInt("multi_touch",0);
//        editor.putInt("fingerprint",0);
//        editor.putInt("payment",0);
//        editor.putInt("battery",0);
//        editor.apply();
//
//    }

    public static void setResultColor(String testId, int result){
        SharedPreferences.Editor editor = App.getMyContext()
                .getSharedPreferences("default_preferences", MODE_PRIVATE).edit();

        editor.putInt(testId,result);
        editor.apply();
    }

    public static int getResultColor(String testId){
        SharedPreferences preferences = App.getMyContext().getSharedPreferences("default_preferences", MODE_PRIVATE);
        int value = preferences.getInt(testId,0);
        return value;
    }

    public static int getTestCount(){
        SharedPreferences preferences = App.getMyContext().getSharedPreferences(test_count_name, MODE_PRIVATE);
        if(preferences!=null){
            testCount = preferences.getInt("count",0);
            setTestCount();
        }
        return testCount;

    }

    private static void setTestCount(){
        SharedPreferences.Editor editor = App.getMyContext()
                .getSharedPreferences(test_count_name, MODE_PRIVATE).edit();
        editor.putInt("count",testCount+1);
        editor.apply();
    }

    public static void setDefaultEnable(){
        SharedPreferences.Editor editor = App.getMyContext()
                .getSharedPreferences(NEXT_ENABLER, MODE_PRIVATE).edit();
        Log.d("+++++++++","ENABLING");
        //Values for enabling/disabling list items
        editor.putInt("display",1);
        editor.putInt("audio",0);
        editor.putInt("touch",0);
        editor.putInt("cpu",0);
        editor.putInt("continuity",0);
        editor.putInt("wifi",0);
        editor.putInt("camera",0);
        editor.putInt("bluetooth",0);
        editor.putInt("storage",0);
        editor.putInt("brightness",0);
        editor.putInt("sensors",0);
        editor.putInt("gps",0);
        editor.putInt("multi_touch",0);
        editor.putInt("fingerprint",0);
        editor.putInt("accelerometer",0);
        editor.putInt("gyroscope",0);
        editor.putInt("magnetometer",0);
        editor.putInt("proximity",0);
        editor.putInt("battery",0);
        editor.putInt("microphone",0);
        editor.apply();
    }

    public static int getNextItem(String item){
        SharedPreferences preferences = App.getMyContext()
                .getSharedPreferences(NEXT_ENABLER, MODE_PRIVATE);
        int nextItem = preferences.getInt(item,0);
        return nextItem;
    }

    public static void setNextItem(String item, int value){
        SharedPreferences.Editor editor = App.getMyContext()
                .getSharedPreferences(NEXT_ENABLER, MODE_PRIVATE).edit();

        editor.putInt(item,value);
        editor.apply();
    }

    public static boolean getBurnInStatus(){

        try{
            Context context = App.getMyContext().createPackageContext("com.example.suma.burninapp",0);
            SharedPreferences preferences = context.getSharedPreferences("burn_in_check",MODE_PRIVATE);
            burn_in_status = preferences.getBoolean("burn_in_status",false);
            Log.d("Shared Value", Boolean.toString(burn_in_status));
            return burn_in_status;

        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
            burn_in_status = false;
        }
        return  burn_in_status;
    }
    public static void setDefaultInstall(){
        SharedPreferences preferences = App.getMyContext().getSharedPreferences("install_checker",MODE_PRIVATE);
        boolean value = preferences.getBoolean("install_check",false);

        //to check if the burn in test has set the value of tested to be true
        SharedPreferences burnIn = App.getMyContext().getSharedPreferences(BURN_IN_CHECK,MODE_PRIVATE);
        if(preferences.getBoolean("install_check",false)){

            Log.d("========","ALREADY INSTALLED TRUE ->"+ value);
        }else{
            //TO Disable the items if not tested.
            SharedAppData.setDefaultEnable();
            Log.d("===========","FIRST INSTALL -> Setting"+ value);
            SharedPreferences.Editor editor = App.getMyContext()
                    .getSharedPreferences(DEFAULT_FILE, MODE_PRIVATE).edit();
            Log.d("=========","SETTING DEFAULT PREFERENCES");
            //Values for the test/retest Check
            editor.putInt("display",0);
            editor.putInt("audio",0);
            editor.putInt("touch",0);
            editor.putInt("cpu",0);
            editor.putInt("continuity",0);
            editor.putInt("wifi",0);
            editor.putInt("scanner",0);
            editor.putInt("bluetooth",0);
            editor.putInt("brightness",0);
            editor.putInt("storage",0);
            editor.putInt("sensors",0);
            editor.putInt("gps",0);
            editor.putInt("applications",0);
            editor.putInt("multi_touch",0);
            editor.putInt("fingerprint",0);
            editor.putInt("payment",0);
            editor.putInt("battery",0);
            editor.putInt("microphone",0);
            editor.apply();
            setFirstInstall();
        }
    }

    public static void setFirstInstall(){
        SharedPreferences test = App.getMyContext().getSharedPreferences("install_checker",0);
        SharedPreferences.Editor editor = test.edit();
//            SharedPreferences.Editor editor = App.getMyContext()
//                    .getSharedPreferences(INSTALL_CHECKER,0 ).edit();
//            Log.d("=========","SETTING DEFAULT INSTALL");
        editor.putBoolean("install_check",true);
        editor.apply();
        editor.commit();

        SharedPreferences preferences = App.getMyContext().getSharedPreferences("install_checker",0);
        boolean value = preferences.getBoolean("install_check",false);
        Log.d("==========","APP ->"+value);

        //Setting BurnIn status
        SharedPreferences burnIn = App.getMyContext().getSharedPreferences(BURN_IN_CHECK, MODE_WORLD_READABLE);
        SharedPreferences.Editor burnEditor = burnIn.edit();
        burnEditor.putBoolean("burn_in_status",true);
        burnEditor.apply();

    }
}
