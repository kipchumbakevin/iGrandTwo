package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.arges.sepan.argmusicplayer.Models.ArgAudio;
import com.arges.sepan.argmusicplayer.PlayerViews.ArgPlayerFullScreenView;
import com.arges.sepan.argmusicplayer.PlayerViews.ArgPlayerLargeView;
import com.bumptech.glide.Glide;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.igrandbusiness.mybusinessplans.utils.Constants;

import java.util.ArrayList;

public class AudioPlayer extends AppCompatActivity {
    String url,title,message,imageurl;
    JcPlayerView jcPlayerView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        imageView = findViewById(R.id.imageview);
        url = getIntent().getExtras().getString("URI");
        imageurl = getIntent().getExtras().getString("IMAGEURL");
        title = getIntent().getExtras().getString("TITLE");
        Glide.with(this)
                .load(imageurl)
                .into(imageView);
        message = "Podcast loading...";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//        jcPlayerView = (JcPlayerView) findViewById(R.id.jcplayerView);
//        jcPlayerView.kill();
//        ArrayList<JcAudio> jcAudios = new ArrayList<>();
//        jcAudios.add(JcAudio.createFromURL(title,url));
//        jcPlayerView.initPlaylist(jcAudios, null);
//        jcPlayerView.createNotification(R.drawable.icon);
        ArgAudio audio = ArgAudio.createFromURL("", title, url);
        ArgPlayerLargeView argMusicPlayer = findViewById(R.id.argmusicplayer);
        argMusicPlayer.disableProgress();
        argMusicPlayer.disableNextPrevButtons();
        argMusicPlayer.enableNotification(this);
        argMusicPlayer.playAudioAfterPercent(10);
        argMusicPlayer.play(audio);
    }

}