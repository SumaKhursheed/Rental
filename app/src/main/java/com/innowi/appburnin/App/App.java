package com.innowi.appburnin.App;

import android.app.Application;
import android.content.Context;

import com.innowi.appburnin.utils.SharedAppData;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Faisal on 2/1/2017.
 * This class will be used to get the context of the
 * app from non-activity classes like ChecLogger
 * Make sure to add the name in the Manifest file
 * for the Application like android:name=".App"
 */

public class App extends Application {
    private static App mApp = null;
    public static Application instance ;

        public static boolean isConnected = false;
    public static int connectedScanner = 0;
    public android.os.Handler handler;
    public static boolean scanSuccess = false;
    public static int result_color;
    public static int count = 0;
    public static boolean scannerState = false;
    private static String currentTime;

    private static final String DEFAULT_FILE = "default_preferences";
    private static final String INSTALL_CHECKER = "install_Checker";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mApp = this;
        SharedAppData.setDefaultInstall();
        //check for first install

//        handler = new android.os.Handler();
        // To register the activity lifecycle and check if the
        //Application is in foreground or background

        //Change it if we need to turn the Scanner On by GPIO
        // And it is not turned on by default in OS.
//        registerActivityLifecycleCallbacks(new lifeTracker());
    }

    public static Context getMyContext(){
        return mApp.getApplicationContext();
    }

    public static String getTime(){
        currentTime  = DateFormat.getDateTimeInstance().format(new Date());;
        return currentTime;
    }



}
