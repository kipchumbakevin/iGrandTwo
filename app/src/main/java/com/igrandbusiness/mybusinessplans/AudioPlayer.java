package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arges.sepan.argmusicplayer.Callbacks.OnCompletedListener;
import com.arges.sepan.argmusicplayer.Callbacks.OnErrorListener;
import com.arges.sepan.argmusicplayer.Callbacks.OnPreparedListener;
import com.arges.sepan.argmusicplayer.Enums.ErrorType;
import com.arges.sepan.argmusicplayer.Models.ArgAudio;
import com.arges.sepan.argmusicplayer.Models.ArgAudioList;
import com.arges.sepan.argmusicplayer.PlayerViews.ArgPlayerFullScreenView;
import com.arges.sepan.argmusicplayer.PlayerViews.ArgPlayerLargeView;
import com.bumptech.glide.Glide;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;
import com.igrandbusiness.mybusinessplans.utils.SharedPreferencesConfig;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class AudioPlayer extends AppCompatActivity {
    String url,title,message,imageurl;
    JcPlayerView jcPlayerView;
    ImageView imageView;
    private ArrayList<ReceiveData> mContentArrayList = new ArrayList<>();
    TextView titelText;
    SharedPreferencesConfig sharedPreferencesConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        imageView = findViewById(R.id.imageview);
        titelText = findViewById(R.id.title);
        url = getIntent().getExtras().getString("URI");
        imageurl = getIntent().getExtras().getString("IMAGEURL");
        title = getIntent().getExtras().getString("TITLE");
        sharedPreferencesConfig = new SharedPreferencesConfig(this);
        Glide.with(this)
                .load(imageurl)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
        message = "Podcast loading...";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//        jcPlayerView = (JcPlayerView) findViewById(R.id.jcplayerView);
//        jcPlayerView.kill();
//        ArrayList<JcAudio> jcAudios = new ArrayList<>();
//        jcAudios.add(JcAudio.createFromURL(title,url));
//        jcPlayerView.initPlaylist(jcAudios, null);
//        jcPlayerView.createNotification(R.drawable.icon);
        titelText.setText(title);
        titelText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        titelText.setSelected(true);
        ArgAudioList playlist = new ArgAudioList(true);
        getPods();
        for (int i=0;i<mContentArrayList.size();i++){
            ArgAudio audio = ArgAudio.createFromURL("", mContentArrayList.get(i).getTitle(), mContentArrayList.get(i).getUrl());
            playlist.add(audio);
        }
       // ArgAudio audio = ArgAudio.createFromURL("", title, url);
        ArgPlayerLargeView argMusicPlayer = findViewById(R.id.argmusicplayer);
//        argMusicPlayer.disableProgress();
//        argMusicPlayer.disableNextPrevButtons();
        argMusicPlayer.enableNotification(this);
        argMusicPlayer.playAudioAfterPercent(5);
       // argMusicPlayer.play(audio);
        argMusicPlayer.playPlaylist(playlist );
        argMusicPlayer.setOnErrorListener((errorType, s) -> argMusicPlayer.disableNotification());
//        argMusicPlayer.setOnPreparedListener(new OnPreparedListener() {
//            @Override
//            public void onPrepared(ArgAudio argAudio, int i) {
//                argMusicPlayer.enableNotification(AudioPlayer.this);
//            }
//        });
        argMusicPlayer.setOnCompletedListener(() -> {

        });
    }
    private void getPods(){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ReceiveData>>() {}.getType();
        ArrayList<ReceiveData>data = gson.fromJson(sharedPreferencesConfig.getPod(),type);
        mContentArrayList.addAll(data);
    }

}