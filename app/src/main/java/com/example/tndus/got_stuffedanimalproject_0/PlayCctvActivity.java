package com.example.tndus.got_stuffedanimalproject_0;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class PlayCctvActivity extends AppCompatActivity {

    VideoView videoView;

    String fileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_cctv);
        videoView = (VideoView)findViewById(R.id.videoView);

        Intent intent = getIntent();
        fileName = intent.getStringExtra("fileName");

        videoView.setVideoURI(Uri.parse("http://tndusdl73.cafe24.com/video/"+fileName+".mp4"));
        videoView.requestFocus();
        videoView.start();


    }

}
