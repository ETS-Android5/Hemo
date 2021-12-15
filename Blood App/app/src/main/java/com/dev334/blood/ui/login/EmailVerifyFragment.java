package com.dev334.blood.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dev334.blood.R;
import com.dev334.blood.model.ApiResponse;
import com.dev334.blood.model.User;
import com.dev334.blood.ui.HomeActivity;
import com.dev334.blood.util.app.AppConfig;
import com.dev334.blood.util.retrofit.ApiClient;
import com.dev334.blood.util.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailVerifyFragment extends Fragment {

    private View view;
    private Button Done;
    private ConstraintLayout parentLayout;
    private static String TAG="EmailVerifyLOG";
    private ProgressBar loading;
    private AppConfig appConfig;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_email_verification, container, false);

        Done=view.findViewById(R.id.verifcationDone);
        parentLayout=view.findViewById(R.id.verifyEmailLayout);
        loading=view.findViewById(R.id.VerificationLoading);

        loading.setVisibility(View.INVISIBLE);
        appConfig=new AppConfig(getContext());

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                SignInUser();
            }
        });

        return view;
    }

    private void SignInUser() {

        String Email=((LoginActivity)getActivity()).getSignUpEmail();
        String Password=((LoginActivity)getActivity()).getSignUpPassword();

        User user = new User(Email,Password);
        Call<ApiResponse> call = ApiClient.getApiClient(getContext()).create(ApiInterface.class).loginUser(user);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(!response.isSuccessful()){
                    if(response.code()==401){
                        Toast.makeText(getContext(), "Email not verified", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.INVISIBLE);
                        return;
                    }
                    Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onResponse: "+response.code());
                    loading.setVisibility(View.INVISIBLE);
                    return;
                }

                Log.i(TAG, "onResponse: "+response.message());
                Log.i(TAG, "onResponse: "+response.headers().get("auth_token"));
                appConfig.setAuthToken(response.headers().get("auth_token"));
                ((LoginActivity)getActivity()).openCreateProfile();
                loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
                loading.setVisibility(View.INVISIBLE);
            }
        });

    }
}