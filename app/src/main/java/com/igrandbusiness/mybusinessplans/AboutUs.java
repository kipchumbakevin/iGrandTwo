package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

public class AboutUs extends AppCompatActivity {
    MaterialCardView whatsapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        whatsapp = findViewById(R.id.whatsapp);

        whatsapp.setOnClickListener(view->goToWhatsapp());
    }

    private void goToWhatsapp() {
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
    }
}