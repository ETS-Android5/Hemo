package com.dev334.blood.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.dev334.blood.database.TinyDB;
import com.dev334.blood.model.ApiResponse;
import com.dev334.blood.model.User;
import com.dev334.blood.R;
import com.dev334.blood.util.app.AppConfig;
import com.dev334.blood.util.retrofit.ApiClient;
import com.dev334.blood.util.retrofit.ApiInterface;
import com.dev334.blood.ui.HomeActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    private View view;
    private Button Login;
    private TextView EditEmail, EditPassword;
    private TextView ForgotPwd, NewUser;
    private String Email,Password;
    private int RC_SIGN_IN=101;
    private ProgressBar loading;
    private ConstraintLayout parentLayout;
    private TinyDB tinyDB;
    private Map<String, Object> map;
    ArrayList<String> interest;
    private static String TAG="LoginFragmentLog";
    private AppConfig appConfig;

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

        ForgotPwd=view.findViewById(R.id.ForgetPasswordLogin);
        NewUser=view.findViewById(R.id.LoginTextSignup);
        EditEmail= view.findViewById(R.id.editEmailLogin);
        EditPassword=view.findViewById(R.id.editPasswordLogin);
        loading=view.findViewById(R.id.loginLoading);
        interest=new ArrayList<>();
        appConfig=new AppConfig(getContext());

        loading.setVisibility(View.INVISIBLE);

        Login=view.findViewById(R.id.btnLogin);

        parentLayout=view.findViewById(R.id.LoginFragmentLayout);

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
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
                loading.setVisibility(View.INVISIBLE);
            }
        });

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