package com.dev334.blood.UI.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.dev334.blood.Database.TinyDB;
import com.dev334.blood.Model.User;
import com.dev334.blood.R;
import com.dev334.blood.Retrofit.RetrofitAPI;
import com.dev334.blood.UI.HomeActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class loginFragment extends Fragment {

    private View view;
    private Button Login, GoogleSignUp;
    private TextView EditEmail, EditPassword;
    private TextView ForgotPwd, NewUser;
    private String Email,Password;
    private int RC_SIGN_IN=101;
    private ProgressBar loading;
    private ConstraintLayout parentLayout;
    private TinyDB tinyDB;
    private Map<String, Object> map;
    ArrayList<String> interest;
    public RetrofitAPI retrofitAPI;
    private static String TAG="LoginFragmentLog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        tinyDB=new TinyDB(getContext());
        map=new HashMap<>();

        GoogleSignUp=view.findViewById(R.id.GoogleSignUpLogin);
        ForgotPwd=view.findViewById(R.id.ForgetPasswordLogin);
        NewUser=view.findViewById(R.id.LoginTextSignup);
        EditEmail= view.findViewById(R.id.editEmailLogin);
        EditPassword=view.findViewById(R.id.editPasswordLogin);
        loading=view.findViewById(R.id.loginLoading);
        interest=new ArrayList<>();

        loading.setVisibility(View.INVISIBLE);

        Login=view.findViewById(R.id.btnLogin);

        parentLayout=view.findViewById(R.id.LoginFragmentLayout);

        Gson gson=new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BaseURL))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofitAPI = retrofit.create(RetrofitAPI.class);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                Email=EditEmail.getText().toString();
                Password=EditPassword.getText().toString();
                if(check(Email,Password)) {
                    SignInUser();
                }else {
                    loading.setVisibility(View.INVISIBLE);
                }
            }
        });

        NewUser.setOnClickListener(v->{
            ((LoginActivity)getActivity()).openSignup();
        });

        ForgotPwd.setOnClickListener(v->{

        });

        return view;
    }

    public static void setSnackBar(View root, String snackTitle) {
        Snackbar snackbar = Snackbar.make(root, snackTitle, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void SignInUser() {


        User user = new User(Email,Password);
        Call<User> call = retrofitAPI.loginUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG, "onResponse: "+response.code());
                    return;
                }
                Log.i(TAG, "onResponse: "+response.message());
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });



    }

    private void checkProfileCreated() {

    }

    private boolean check(String email, String password) {
        if(email.isEmpty()){
            EditEmail.setError("Email is empty");
            return false;
        }else if(password.isEmpty()){
            EditPassword.setError("Password is empty");
            return  false;
        }else {
            if (password.length() < 6) {
                EditPassword.setError("Password is too short");
                return false;
            } else {
                return true;
            }
        }
    }

}