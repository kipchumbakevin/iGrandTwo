package com.igrandbusiness.mybusinessplans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.iid.FirebaseInstanceId;



public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private static final String CHANNEL_ID = "101";
    private final int ID_Home = 1;
    private final int ID_Podcast = 2;
    private final int ID_Videos = 3;
    private final int ID_Magazines = 4;
    private final int ID_Calc = 5;
    MeowBottomNavigation meowBottomNavigation;
    TextView igrandTitle;
    FrameLayout frameLayout;
    CardView faceb,twitter,telegram,mail,linkedin,whatsapp;
    String phone;
    BottomSheetBehavior bottomSheetBehavior;
    static String deviceId;
    ConstraintLayout bottom;
    ImageView menuImageView;
    int ads,from;
    boolean oo,ff,homeIsLoaded = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom = findViewById(R.id.bottom);
        whatsapp = findViewById(R.id.whatsapp);
        faceb = findViewById(R.id.fb);
        twitter = findViewById(R.id.twitter);
        telegram = findViewById(R.id.telegram);
        mail = findViewById(R.id.mail);
        linkedin = findViewById(R.id.linkedin);
        phone = "0755907382";
        ads = 0;

        deviceId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom);
        if (getIntent().hasExtra("FROM")) {
            from = Integer.parseInt(getIntent().getExtras().getString("FROM"));
        }
        createNotificationChannel();
        getToken();

        frameLayout = findViewById(R.id.frame_layout);
        igrandTitle = findViewById(R.id.igrand_title);
        menuImageView = findViewById(R.id.menu_imageview);
        meowBottomNavigation = findViewById(R.id.bottomNavigation);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_Home,R.drawable.ic_baseline_home_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_Podcast,R.drawable.ic_baseline_music_note_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_Videos,R.drawable.ic_baseline_videocam_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_Magazines,R.drawable.ic_baseline_menu_book_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_Calc,R.drawable.ic_calc));

        meowBottomNavigation.setOnClickMenuListener(item -> {
        });
        meowBottomNavigation.setOnShowListener(item -> {
            switch (item.getId()){
                case ID_Home:
                    loadHome();
                break;
                case ID_Magazines:
                    loadMagazines();
                break;
                case ID_Podcast:
                    loadPodcasts();
                break;
                case ID_Videos:
                    loadVideos();
                break;
                case ID_Calc:
                    startActivity(new Intent(MainActivity.this,CalcActivity.class));
                    finish();
                    break;
                default:
            }
        });
        meowBottomNavigation.setOnReselectListener(item -> {
            switch (item.getId()){
                case ID_Home: loadHome();
                    break;
                case ID_Magazines: loadMagazines();
                    break;
                case ID_Podcast: loadPodcasts();
                    break;
                case ID_Videos: loadVideos();
                    break;
                default:
            }
        });
        if (from == 1){
            meowBottomNavigation.show(ID_Videos,true);
        }else{
            meowBottomNavigation.show(ID_Home, true);
        }
        faceb.setOnClickListener(view -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            oo = true;
            Uri uri = Uri.parse("https://www.facebook.com/iGrandbp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/iGrandbp")));
            }
        });
        twitter.setOnClickListener(view -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            oo = true;
            Uri uri = Uri.parse("https://twitter.com/DiraLaBiashara");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://twitter.com/DiraLaBiashara")));
            }
        });
        whatsapp.setOnClickListener(view->{
            try {
                PackageManager pm = getPackageManager();
                pm.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone=+254755907382"));
                startActivity(sendIntent);

            }
            catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(this, "Whatsapp not installed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
//            sendIntent.putExtra(Intent.EXTRA_TEXT,"Sample text");
//            sendIntent.setType("text/plain");
//            sendIntent.setPackage("com.whatsapp");
//            startActivity(sendIntent);
        });
        mail.setOnClickListener(view -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            oo = true;
            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.setData(Uri.parse("mailto:"));
            email.putExtra(Intent.EXTRA_EMAIL,  new String[]{"hello@igrandbp.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, "Customer Service");
            if (email.resolveActivity(getPackageManager())!=null){
                startActivity(email);
            }else {
                Toast.makeText(MainActivity.this, "There is no app to perform this action", Toast.LENGTH_SHORT).show();
            }
            //need this to prompts email client only
           // email.setType("message/rfc822");

            //startActivity(Intent.createChooser(email, "Choose an Email client :"));
        });

        telegram.setOnClickListener(view -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            oo = true;
            Uri uri = Uri.parse("https://t.me/iGrandbp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://t.me/iGrandbp")));
            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                oo = true;
                Uri uri = Uri.parse("https://www.linkedin.com/company/igrand-business-plans/");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.linkedin.com/company/igrand-business-plans/")));
                }
            }
        });
    }
//
//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putString("Bug","value");
//        super.onSaveInstanceState(outState);
//    }

    public void showPopup(View v){
        if (!oo) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            oo = true;
        }
        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.pop_up);
        popupMenu.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.call:
                ff = true;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ phone));
                startActivity(intent);
                return true;
            case R.id.find:
                if (oo) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    oo = false;
                }else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    oo = true;
                }
                return true;
            case R.id.share:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                ff = true;
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.setType("text/plain");
                String shareBody = "Unmatched creativity in well crafted business plans.\n" +
                        "Download iGrand Business Plans app now at https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName();
                intent1.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                intent1.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent1, "Share via"));
                return true;
            case R.id.rate:
                ff = true;
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
                }
                return true;
            case R.id.about:
                startActivity(new Intent(this,AboutUs.class));
                return true;
            default:
                return false;
        }
    }
    private void loadHome(){
        igrandTitle.setText("Editorials");
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, homeFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        homeIsLoaded = true;
//        Home fragment = new Home();
//        FragmentManager manager = getSupportFragmentManager();
//        manager.beginTransaction().replace(R.id.fragments,fragment,fragment.getTag()).commit();
    }
    private void loadMagazines(){
        igrandTitle.setText("Magazines");
        MagazinesFragment magazinesFragment = new MagazinesFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, magazinesFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        homeIsLoaded = false;
    }
    private void loadPodcasts(){
        igrandTitle.setText("Podcasts");
        PodcastFragment podcastFragment = new PodcastFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, podcastFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        homeIsLoaded = false;
    }
    private void loadVideos(){
        igrandTitle.setText("Vlogs");
        VideosFragment videosFragment = new VideosFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, videosFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        homeIsLoaded = false;
    }


    @Override
    public void onBackPressed() {
        if (homeIsLoaded) {
            super.onBackPressed();
        }else {
            meowBottomNavigation.show(ID_Home,true);
            //loadHome();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //get application token for firebase notif
    private void getToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(instanceIdResult -> Log.e("Token: ", instanceIdResult.getToken()));
    }
    //create notif channel
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "firebaseNotifChannel";
            String description = "this is the channel to receive firebase notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void saveDeviceId(){


    }
}