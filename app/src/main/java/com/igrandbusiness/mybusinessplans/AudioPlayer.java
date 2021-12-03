package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;

import java.util.ArrayList;

public class AudioPlayer extends AppCompatActivity {
    String url,title;
    JcPlayerView jcPlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        url = getIntent().getExtras().getString("URI");
        title = getIntent().getExtras().getString("TITLE");
        jcPlayerView = (JcPlayerView) findViewById(R.id.jcplayerView);
        jcPlayerView.kill();
        ArrayList<JcAudio> jcAudios = new ArrayList<>();
        jcAudios.add(JcAudio.createFromURL(title,url));
        jcPlayerView.initPlaylist(jcAudios, null);
        jcPlayerView.createNotification(R.drawable.icon);
    }

}