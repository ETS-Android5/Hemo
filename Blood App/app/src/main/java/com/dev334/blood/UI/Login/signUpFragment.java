package com.dev334.blood.UI.Login;

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
import android.widget.TextView;
import android.widget.Toast;

import com.dev334.blood.Model.User;
import com.dev334.blood.R;
import com.dev334.blood.Retrofit.RetrofitAPI;
import com.dev334.blood.UI.HomeActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class signUpFragment extends Fragment {

    private View view;
    private Button SignUp, GoogleSignUp;
    private TextView EditEmail, EditPassword, Login,EditName;
    private String Email,Password,Name;
    private int RC_SIGN_IN=101;
    private ProgressBar loading;
    private ConstraintLayout parentLayout;
    private ArrayList<String> interest;
    public RetrofitAPI retrofitAPI;
    private String TAG="signUpFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_signup, container, false);

        GoogleSignUp=view.findViewById(R.id.GoogleSignUp);

        EditEmail= view.findViewById(R.id.editEmailSignup);
        EditPassword=view.findViewById(R.id.editPasswordSignUp);
        loading=view.findViewById(R.id.SignUpLoading);
        Login=view.findViewById(R.id.LoginTextSignup);
        EditName=view.findViewById(R.id.editUserName);

        Gson gson=new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(getString(R.string.BaseURL))
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();

        retrofitAPI = retrofit.create(RetrofitAPI.class);
        Login.setOnClickListener(v->{
            ((LoginActivity)getActivity()).openLogin();
        });

        interest=new ArrayList<>();

        loading.setVisibility(View.INVISIBLE);
        SignUp=view.findViewById(R.id.SignUpButton);

        parentLayout=view.findViewById(R.id.signUpFragmentLayout);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                Email=EditEmail.getText().toString();
                Name=EditName.getText().toString();
                Password=EditPassword.getText().toString();

                if(check(Name,Email,Password)){
                   registerUser();
                }
            }
        });


        GoogleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void registerUser() {
        User user = new User(Name,Email,Password);
        Call<User> call = retrofitAPI.registerUser(user);
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

    private boolean check(String name,String email, String password) {
        if(name.isEmpty()){
            EditName.setError("Name is empty");
            return false;
        }
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