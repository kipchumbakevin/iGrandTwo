package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private final int ID_Home = 1;
    private final int ID_Podcast = 2;
    private final int ID_Videos = 3;
    private final int ID_Magazines = 4;
    MeowBottomNavigation meowBottomNavigation;
    TextView igrandTitle;
    FrameLayout frameLayout;
    CardView faceb,twitter,telegram,mail,linkedin;
    String phone;
    BottomSheetBehavior bottomSheetBehavior;
    ConstraintLayout bottom;
    ImageView menuImageView;
    boolean oo,ff = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom = findViewById(R.id.bottom);
        faceb = findViewById(R.id.fb);
        twitter = findViewById(R.id.twitter);
        telegram = findViewById(R.id.telegram);
        mail = findViewById(R.id.mail);
        linkedin = findViewById(R.id.linkedin);
        phone = "+254795801971";
        bottomSheetBehavior = BottomSheetBehavior.from(bottom);

        frameLayout = findViewById(R.id.frame_layout);
        igrandTitle = findViewById(R.id.igrand_title);
        menuImageView = findViewById(R.id.menu_imageview);
        meowBottomNavigation = findViewById(R.id.bottomNavigation);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_Home,R.drawable.ic_baseline_home_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_Podcast,R.drawable.ic_baseline_music_note_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_Videos,R.drawable.ic_baseline_videocam_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_Magazines,R.drawable.ic_baseline_menu_book_24));

        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }
        });
        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
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
            }
        });
        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
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
            }
        });
        meowBottomNavigation.show(ID_Home,true);
        faceb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                oo = true;
                Uri uri = Uri.parse("https://twitter.com/iGrandbp");
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
                            Uri.parse("https://twitter.com/iGrandbp")));
                }
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                oo = true;
                Intent email = new Intent(Intent.ACTION_SENDTO);
                email.setData(Uri.parse("mailto:"));
                email.putExtra(Intent.EXTRA_EMAIL,  new String[]{"igrandbp@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Customer Service");
                if (email.resolveActivity(getPackageManager())!=null){
                    startActivity(email);
                }
                //need this to prompts email client only
               // email.setType("message/rfc822");

                //startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            default:
                return false;
        }
    }
    private void loadHome(){
        igrandTitle.setText("iGrand Business News");
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, homeFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
//        Home fragment = new Home();
//        FragmentManager manager = getSupportFragmentManager();
//        manager.beginTransaction().replace(R.id.fragments,fragment,fragment.getTag()).commit();
    }
    private void loadMagazines(){
        igrandTitle.setText("iGrand Business Magazines");
        MagazinesFragment magazinesFragment = new MagazinesFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, magazinesFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    private void loadPodcasts(){
        igrandTitle.setText("iGrand Business Podcasts");
        PodcastFragment podcastFragment = new PodcastFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, podcastFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    private void loadVideos(){
        igrandTitle.setText("iGrand Business Videos");
        VideosFragment videosFragment = new VideosFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, videosFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

}