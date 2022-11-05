package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arges.sepan.argmusicplayer.Callbacks.OnCompletedListener;
import com.arges.sepan.argmusicplayer.Callbacks.OnErrorListener;
import com.arges.sepan.argmusicplayer.Callbacks.OnPlaylistAudioChangedListener;
import com.arges.sepan.argmusicplayer.Callbacks.OnPreparedListener;
import com.arges.sepan.argmusicplayer.Enums.ErrorType;
import com.arges.sepan.argmusicplayer.Models.ArgAudio;
import com.arges.sepan.argmusicplayer.Models.ArgAudioList;
import com.arges.sepan.argmusicplayer.Models.ArgNotificationOptions;
import com.arges.sepan.argmusicplayer.PlayerViews.ArgPlayerFullScreenView;
import com.arges.sepan.argmusicplayer.PlayerViews.ArgPlayerLargeView;
import com.bumptech.glide.Glide;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;
import com.igrandbusiness.mybusinessplans.utils.Constants;
import com.igrandbusiness.mybusinessplans.utils.SharedPreferencesConfig;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class AudioPlayer extends AppCompatActivity {
    String url,title,message,imageurl;
    JcPlayerView jcPlayerView;
    ImageView imageView;
    private ArrayList<ReceiveData> mContentArrayList = new ArrayList<>();
    TextView titelText;
    int pos,id;
    SharedPreferencesConfig sharedPreferencesConfig;
    ArgPlayerLargeView argMusicPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        imageView = findViewById(R.id.imageview);
        titelText = findViewById(R.id.title);
        url = getIntent().getExtras().getString("URI");
        imageurl = getIntent().getExtras().getString("IMAGEURL");
        id = Integer.parseInt(getIntent().getExtras().getString("ID"));
        title = getIntent().getExtras().getString("TITLE");
        pos = Integer.parseInt(getIntent().getExtras().getString("pos"));
        sharedPreferencesConfig = new SharedPreferencesConfig(this);
        Constants.saveUsageStat(this,id,2);
        Glide.with(this)
                .load(imageurl)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
        message = "Podcast loading...";
//        jcPlayerView = (JcPlayerView) findViewById(R.id.jcplayerView);
//        jcPlayerView.kill();
//        ArrayList<JcAudio> jcAudios = new ArrayList<>();
//        jcAudios.add(JcAudio.createFromURL(title,url));
//        jcPlayerView.initPlaylist(jcAudios, null);
//        jcPlayerView.createNotification(R.drawable.icon);
        titelText.setText(title);
        titelText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        ArgAudioList playlist = new ArgAudioList(true);
        getPods();
        for (int i=0;i<mContentArrayList.size();i++){
            ArgAudio audio = ArgAudio.createFromURL("", mContentArrayList.get(i).getTitle(), mContentArrayList.get(i).getUrl());
            playlist.add(audio);
        }
       // ArgAudio audio = ArgAudio.createFromURL("", title, url);
        argMusicPlayer = findViewById(R.id.argmusicplayer);
//        argMusicPlayer.disableProgress();
//        argMusicPlayer.disableNextPrevButtons();
        argMusicPlayer.enableNotification(new ArgNotificationOptions(this)
                .setProgressEnabled(true)
                .setImageResoureId(R.drawable.icon));
        argMusicPlayer.playAudioAfterPercent(5);
        playlist.goTo(pos);
        argMusicPlayer.playPlaylist(playlist);
        argMusicPlayer.setOnPlaylistAudioChangedListener((playlist1, currentAudioIndex) -> {
            String ii = playlist1.get(currentAudioIndex).getTitle();
            titelText.setText(ii);
            titelText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            ii = ii.replaceAll("\\s", "");
            for (int i = 0;i < mContentArrayList.size();i++){
                String nn = "- "+mContentArrayList.get(i).getTitle();
                nn=nn.replaceAll("\\s", "");
                if (nn.equalsIgnoreCase(ii)){
                    Constants.saveUsageStat(AudioPlayer.this,mContentArrayList.get(i).getId(),2);
                }
            }
        });
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