package com.dev334.blood.ui.login;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dev334.blood.database.TinyDB;
import com.dev334.blood.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private LoginHomeFragment loginHome;
    private FragmentManager fragmentManager;
    private SignUpFragment SignupFrag;
    private LoginFragment loginFrag;
    private EmailVerifyFragment verificationFragment;
    private com.dev334.blood.ui.login.CreateProfileFragment CreateProfileFragment;
    private String PhoneNo,Username,Organisation,Facebook, Instagram;
    private ArrayList<String> Organisations,userInterest;
    private TinyDB tinyDB;
    private TextView loginTxt;
    private int FRAGMENT=0; //0>default 1->emailVerification

    private String email, password,  phoneNo, verificationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginHome=new LoginHomeFragment();
        SignupFrag=new SignUpFragment();
        loginFrag=new LoginFragment();
        verificationFragment=new EmailVerifyFragment();
        CreateProfileFragment=new CreateProfileFragment();

        tinyDB=new TinyDB(getApplicationContext());

        fragmentManager=getSupportFragmentManager();

        FRAGMENT=getIntent().getIntExtra("FRAGMENT",0);

        if(FRAGMENT==0){
            replaceFragment(loginHome);
        }else{
            replaceFragment(loginFrag);
        }

    }

    public void openLogin(){
        replaceFragment(loginFrag);
    }

    public void openSignup(){
        replaceFragment(SignupFrag);
    }

    public void openVerifyEmail(){
        replaceFragment(verificationFragment);
    }

    public void openCreateProfile(){
        replaceFragment(CreateProfileFragment);
    }


    public void setSignUpCredentials(String email, String password){
        this.email=email;
        this.password=password;
    }

    public String getSignUpEmail(){
        return email;
    }

    public String getSignUpPassword(){
        return password;
    }

    private void replaceFragment(Fragment fragmentToShow) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        // Hide all of the fragments
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            transaction.hide(fragment);
        }

        if (fragmentToShow.isAdded()) {
            // When fragment was previously added - show it
            transaction.show(fragmentToShow);
        } else {
            // When fragment is adding first time - add it
            transaction.add(R.id.LoginContainer, fragmentToShow);
        }

        transaction.commit();
    }
}