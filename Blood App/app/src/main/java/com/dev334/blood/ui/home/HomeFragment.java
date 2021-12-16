package com.dev334.blood.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev334.blood.R;

public class HomeFragment extends Fragment {

    public static HomeFragment fragment=null;
    private View view;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        if(fragment==null){
            fragment = new HomeFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }
}