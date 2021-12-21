package com.dev334.blood.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev334.blood.R;
import com.dev334.blood.databinding.FragmentNotificationBinding;

public class NotificationFragment extends Fragment {

    public static NotificationFragment fragment=null;
    private View view;
    FragmentNotificationBinding binding;

    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance() {
        if(fragment==null) {
            fragment = new NotificationFragment();
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
        binding = FragmentNotificationBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}