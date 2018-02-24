package com.innowi.appburnin;

        import android.Manifest;
        import android.app.ActivityManager;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.BatteryManager;
        import android.os.Build;
        import android.os.Bundle;
        import android.app.Activity;
        import android.os.SystemClock;
        import android.provider.Settings;
        import android.support.v4.app.ActivityCompat;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import com.innowi.appburnin.App.App;
        import com.innowi.appburnin.utils.JSONHelper;

        import java.io.IOException;
        import java.io.RandomAccessFile;

public class Main extends Activity {
    long beforePing, afterPing;
    int loopCount;
    String pingTime;
    private Intent batteryStatus;
    private IntentFilter batteryFilter;
    TextView totaltime;
    Button start, stop;
    private int status, chargePlug, batteryCapacity, batteryPercent, batteryTemperature;
    String[] Array = {"Video"};
    String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.startsep);
        totaltime = findViewById(R.id.time);

        batteryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryStatus = registerReceiver(null, batteryFilter);
        batteryTemperature = batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        batteryPercent = (int) ((level / (float) scale) * 100f);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beforePing = SystemClock.currentThreadTimeMillis();
                Intent intent = new Intent(Main.this, VideoViewActivity.class);
                startActivity(intent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterPing = SystemClock.currentThreadTimeMillis()- beforePing;
                pingTime = Long.toString(afterPing);
                totaltime.setText(pingTime);
                Log.d("The time is", pingTime+" ms");

                logJson();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
        settingPermission();

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;}

    private void settingPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 200);

            }
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        logJson();
        finish();
    }

    private float readUsage() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();

            String[] toks = load.split(" ");

            long idle1 = Long.parseLong(toks[5]);
            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            try {
                Thread.sleep(360);
            } catch (Exception e) {}

            reader.seek(0);
            load = reader.readLine();
            reader.close();

            toks = load.split(" ");

            long idle2 = Long.parseLong(toks[5]);
            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            float num = (float)(cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));
            return num * 100;
            //return (float)(cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return 0;
    }

    private long getAvailableRam(){
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.availMem / 1048576L;
        return availableMegs;
    }

    private long getTotalRam(){
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        //long availableMegs = mi.availMem / 1048576L;
        Long totalMegs = mi.totalMem / 1048576L;
        return totalMegs;
    }

    private void logJson(){
        JSONHelper.initJSON("burn_in");
        JSONHelper.setType("burn_in");
        JSONHelper.setDescription("BURN IN TEST");
        JSONHelper.setLowLimit(0);
        JSONHelper.setUpLimit(0);
        JSONHelper.setResultValue(pingTime);
        JSONHelper.setStatus("n/a");
        JSONHelper.setMiscData("Total Loops : " + Integer.toString(VideoViewActivity.loopCount) +"\n" +
                " Total Time :" + pingTime +"\n" +
                "Battery Percent: "+Integer.toString(batteryPercent) + " %" +"\n"+
                "Battery temperature: "+Integer.toString(batteryTemperature) + " °C" +"\n"+
                "CPU Usage: " + readUsage() + " %" +"\n"+
                "Total RAM: " + Long.toString(getTotalRam()) + " MB" +"\n"+
                "Available Ram: " + getAvailableRam() + " MB");
        JSONHelper.setTime(App.getTime());

        JSONHelper.logJSON();
    }

}
