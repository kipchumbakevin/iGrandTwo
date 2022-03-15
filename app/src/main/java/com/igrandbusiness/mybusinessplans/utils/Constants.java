package com.igrandbusiness.mybusinessplans.utils;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.igrandbusiness.mybusinessplans.R;

public class Constants {
    public static final String BASE_URL="https://igrandsub.igrandbp.com/";
    //public static final String BASE_URL="http://192.168.100.55:8000/";
    public static final String ACTIVE_CONSTANT = "Active";

    public static void setWindowFlag(Activity activity, final int bits, boolean on){
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on){
            winParams.flags |= bits;
        }else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    public static void networkError(Activity activity, CardView networkErrorCard, TextView networkError){
        networkError.setText(activity.getResources().getString(R.string.netwrokError));
        networkErrorCard.setVisibility(View.VISIBLE);
    }
    public static void serverError(Activity activity,CardView networkErrorCard, TextView networkError){
        networkError.setText(activity.getResources().getString(R.string.serverError));
        networkErrorCard.setVisibility(View.VISIBLE);
    }
}
