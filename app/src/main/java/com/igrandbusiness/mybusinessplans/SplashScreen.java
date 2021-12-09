package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.igrandbusiness.mybusinessplans.adapters.OnboardingAdapter;
import com.igrandbusiness.mybusinessplans.models.OnboardingItem;
import com.igrandbusiness.mybusinessplans.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {
    CountDownTimer countDownTimer;
    private OnboardingAdapter onboardingAdapter;
    private LinearLayoutCompat layoutCompatIndicator;
    private MaterialButton buttonOnboardingAction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        layoutCompatIndicator = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);
        setupOnboardingItems();

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);
        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);
        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });
        buttonOnboardingAction.setOnClickListener(view->{
            if (onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
            }else {
                startActivity(new Intent(SplashScreen.this,MainActivity.class)
                        .putExtra("FROM",Integer.toString(2)));
                finish();
            }
        });
        //       getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        if (Build.VERSION.SDK_INT >= 21) {
//            Constants.setWindowFlag(this,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,false);
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//        countDownTimer = new CountDownTimer(2000,1000) {
//            @Override
//            public void onTick(long l) {
//            }
//            @Override
//            public void onFinish() {
//                startActivity(new Intent(SplashScreen.this,MainActivity.class)
//                .putExtra("FROM",Integer.toString(2)));
//                finish();
//            }
//        }.start();
    }
    private void setupOnboardingItems(){
        List<OnboardingItem>onboardingItems = new ArrayList<>();
        OnboardingItem one = new OnboardingItem();
        one.setTitle("Read insights to help you grow your business");
        one.setImage(R.drawable.one);

        OnboardingItem two = new OnboardingItem();
        two.setTitle("Listen to and watch our podcasts and video reviews.");
        two.setImage(R.drawable.two);

        OnboardingItem three = new OnboardingItem();
        three.setTitle("Download and read magazines every month.");
        three.setImage(R.drawable.three);

        onboardingItems.add(one);
        onboardingItems.add(two);
        onboardingItems.add(three);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }
    private void setupOnboardingIndicators(){
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0; i < indicators.length; i ++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            layoutCompatIndicator.addView(indicators[i]);
        }
    }
    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutCompatIndicator.getChildCount();
        for (int i = 0; i < childCount; i ++){
            ImageView imageView = (ImageView) layoutCompatIndicator.getChildAt(i);
            if (i == index){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.onboarding_indicator_active));
            }else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.onboarding_indicator_inactive));
            }
        }
        if (index == onboardingAdapter.getItemCount() - 1){
            buttonOnboardingAction.setText("Start");
        }else {
            buttonOnboardingAction.setText("Next");
        }
    }
    @Override
    protected void onDestroy() {
       // countDownTimer.cancel();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
       // countDownTimer.cancel();
        super.onBackPressed();
    }
}