package com.dev334.blood.util.app;

import android.content.Context;
import android.util.Log;

import com.dev334.blood.database.TinyDB;
import com.dev334.blood.model.User;

public class AppConfig {
    private Context context;
    private TinyDB tinyDB;
    private final String TAG="AppConfigLog";

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
        Log.i(TAG, "setAuthToken: "+token);
        tinyDB.putString("AuthToken", token);
    }

    public User getUserInfo(){
        return tinyDB.getObject("User", User.class);
    }

    public void setUserInfo(User user){
        tinyDB.putObject("User", user);
        Log.i(TAG, "setUserInfo: "+user.getName());
    }

    public void setProfileCreated(Boolean status){
        tinyDB.putBoolean("Profile", status);
    }

    public boolean isProfileCreated(){
        return tinyDB.getBoolean("Profile");
    }

}
