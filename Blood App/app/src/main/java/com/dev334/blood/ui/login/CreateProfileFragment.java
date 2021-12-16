package com.dev334.blood.ui.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.dev334.blood.database.TinyDB;
import com.dev334.blood.R;

import java.util.ArrayList;

public class CreateProfileFragment extends Fragment {

    private View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_profile, container, false);
        return view;
    }



    private boolean check() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    
}