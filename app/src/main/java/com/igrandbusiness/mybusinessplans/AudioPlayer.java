package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.arges.sepan.argmusicplayer.Models.ArgAudio;
import com.arges.sepan.argmusicplayer.PlayerViews.ArgPlayerFullScreenView;
import com.arges.sepan.argmusicplayer.PlayerViews.ArgPlayerLargeView;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;

import java.util.ArrayList;

public class AudioPlayer extends AppCompatActivity {
    String url,title,message;
    JcPlayerView jcPlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        url = getIntent().getExtras().getString("URI");
        title = getIntent().getExtras().getString("TITLE");
        message = "Podcast loading";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//        jcPlayerView = (JcPlayerView) findViewById(R.id.jcplayerView);
//        jcPlayerView.kill();
//        ArrayList<JcAudio> jcAudios = new ArrayList<>();
//        jcAudios.add(JcAudio.createFromURL(title,url));
//        jcPlayerView.initPlaylist(jcAudios, null);
//        jcPlayerView.createNotification(R.drawable.icon);
        ArgAudio audio = ArgAudio.createFromURL("", title, url);
        ArgPlayerLargeView argMusicPlayer = (ArgPlayerLargeView) findViewById(R.id.argmusicplayer);
        argMusicPlayer.disableProgress();
        argMusicPlayer.disableNextPrevButtons();
        argMusicPlayer.enableNotification(this);
        argMusicPlayer.playAudioAfterPercent(20);
        argMusicPlayer.play(audio);
    }

}