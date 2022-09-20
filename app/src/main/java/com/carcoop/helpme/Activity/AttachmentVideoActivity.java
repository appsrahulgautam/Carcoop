package com.carcoop.helpme.Activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.carcoop.helpme.Constance;
import com.carcoop.helpme.R;

import java.io.File;

public class AttachmentVideoActivity extends BaseActivity {

    private static String TAG = "AttachmentVideoActivity";

    private RelativeLayout rootLayout;
    private VideoView videoView;
    private ProgressBar progressBar;
    private MediaController mediaController;
    private File file = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_attachment_video);

        Log.e(TAG, "onCreate: ");
//        if (getIntent().getStringExtra(ConstantString.CHAT_IMAGE_VIEW) != null) {
//            Log.e(TAG, "ifcondition when true " );
//            viewimage = new File(getIntent().getStringExtra(ConstantString.CHAT_IMAGE_VIEW));
//
//        }
//        else{
//            Log.e(TAG, "ifcondition when false file-> "+viewimage );
//        }
//        if (getIntent().getStringExtra(ConstantString.ATTACHMENT_TYPE) != null) {
//
//            type = getIntent().getStringExtra(ConstantString.ATTACHMENT_TYPE);
//        }
        initUI();
        loadVideo();


    }

    private void initUI() {

        rootLayout = findViewById(R.id.layout_root);
        videoView = findViewById(R.id.vv_full_view);
        progressBar = findViewById(R.id.progress_show_video);

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaController.show(2000);
            }
        });
    }

    private void loadVideo() {
        progressBar.setVisibility(View.VISIBLE);
        String filename = getIntent().getStringExtra(Constance.VIDEO_NAME);
        assert filename != null;
        File file = new File(filename);

        if (file != null) {
            mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.setVideoPath(file.getPath());
            videoView.start();
        }

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
                mediaController.show(2000);
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                progressBar.setVisibility(View.GONE);
                mediaController.hide();
                return true;
            }
        });
    }
}