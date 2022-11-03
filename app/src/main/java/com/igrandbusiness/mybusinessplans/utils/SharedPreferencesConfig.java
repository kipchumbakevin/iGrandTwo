package com.igrandbusiness.mybusinessplans.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.igrandbusiness.mybusinessplans.R;
import com.igrandbusiness.mybusinessplans.models.ReceiveData;

import java.util.ArrayList;

public class SharedPreferencesConfig {
    private SharedPreferences sharedPreferences;
    private Context context;
    public SharedPreferencesConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.SHARED_PREFERENCES), Context.MODE_PRIVATE);
    }

    public void saveAuthenticationInformation(String status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(context.getResources().getString(R.string.CLIENTS_TOKEN),accessToken);
        editor.putString(context.getResources().getString(R.string.CLIENTS_STATUS),status);
//        editor.putString(context.getResources().getString(R.string.CLIENTS_PHONE),phone);
//        editor.putString(context.getResources().getString(R.string.CLIENTS_EMAIL),email);

        editor.commit();
    }

    public String readClientsStatus(){
        String status;
        status = sharedPreferences.getString(context.getResources().getString(R.string.CLIENTS_STATUS),"");
        return status;
    }
    public String readClientsAccessToken(){
        String accessToken;
        accessToken = sharedPreferences.getString(context.getResources().getString(R.string.CLIENTS_TOKEN),"");
        return accessToken;
    }
    public String readClientsPhone(){
        String phone;
        phone = sharedPreferences.getString(context.getResources().getString(R.string.CLIENTS_PHONE),"");
        return  phone;
    }
    public String readClientsEmail(){
        String email;
        email = sharedPreferences.getString(context.getResources().getString(R.string.CLIENTS_EMAIL),"");
        return  email;
    }
    public void savePod(ArrayList<ReceiveData> properties){
        Gson gson = new Gson();
        String data = gson.toJson(properties);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.POD),data);

        editor.apply();
    }
    public void clearPod() {
        String data;
        data = sharedPreferences.getString(context.getResources().getString(R.string.POD),"");
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(data).apply();
    }
    public String getPod(){
        String properties;
        properties = sharedPreferences.getString(context.getResources().getString(R.string.POD),"");
        return properties;
    }


    public  boolean isloggedIn(){
        return readClientsStatus().equals(Constants.ACTIVE_CONSTANT);
    }

    public void clear() {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear()
                .apply();
    }
}
