package com.dev334.blood.util.app;

import android.content.Context;
import android.util.Log;

import com.dev334.blood.database.TinyDB;

public class AppConfig {
    private Context context;
    private TinyDB tinyDB;

    public AppConfig(Context context){
        this.context=context;
        tinyDB=new TinyDB(context);
    }

    public boolean isUserLogin(){
        return tinyDB.getBoolean("Login");
    }

    public void setLoginStatus(Boolean status){
        tinyDB.putBoolean("Login", status);
    }

    public String getAuthToken(){
        return tinyDB.getString("AuthToken");
    }

    public void setAuthToken(String token){
        Log.i("AuthTokenSaved", "setAuthToken: "+token);
        tinyDB.putString("AuthToken", token);
    }

}
