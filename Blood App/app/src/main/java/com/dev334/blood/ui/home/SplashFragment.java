package com.dev334.blood.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dev334.blood.R;
import com.dev334.blood.model.BloodReq;
import com.dev334.blood.model.User;
import com.dev334.blood.ui.login.LoginActivity;
import com.dev334.blood.util.app.AppConfig;
import com.dev334.blood.util.retrofit.ApiClient;
import com.dev334.blood.util.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashFragment extends Fragment {

    private View view;
    private AppConfig appConfig;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_splash, container, false);

        if(!appConfig.isUserLogin()){
            ((HomeActivity)getActivity()).openLoginActivity(0);
        }else if(!appConfig.isProfileCreated()){
            ((HomeActivity)getActivity()).openLoginActivity(1);
        }else{
            BloodReq bloodReq= new BloodReq(((HomeActivity)getActivity()).getUserLocation(), ((HomeActivity)getActivity()).getUserBlood());

        }




        ((HomeActivity)getActivity()).openHomeFragment();

        return view;
    }
}