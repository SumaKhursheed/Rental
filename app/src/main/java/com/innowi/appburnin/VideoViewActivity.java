package com.innowi.appburnin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.innowi.appburnin.utils.SharedAppData;

public class VideoViewActivity extends Activity {

    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview;
    ImageView cancel;
    static int loopCount;

    // Insert Video URL
    //String stringPath = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
   // String stringPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "DCIM/Camera/VID_20171029_151755.mp4";
    String stringPath = "android.resource://com.example.suma.burninapp/" + R.raw.videocheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);
        videoview = (VideoView) findViewById(R.id.VideoView);

        loopCount = SharedAppData.getTestCount();
        Log.d("Loop Count == ", Integer.toString(loopCount));

        cancel = (ImageView) findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VideoViewActivity.this, Main.class);
                startActivity(intent);
            }

        });

        // Create progressbar
        pDialog = new ProgressDialog(VideoViewActivity.this);
        // Set progressbar title
        pDialog.setTitle("Android Video Streaming");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    VideoViewActivity.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String stringPath
            Uri video = Uri.parse(stringPath);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }
        });

        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion (MediaPlayer mp) {
                // return to Main Activity once the video completes
                Intent intent = new Intent(VideoViewActivity.this, VideoViewActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(VideoViewActivity.this, Main.class);
        startActivity(intent);
        finish();
        return;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
