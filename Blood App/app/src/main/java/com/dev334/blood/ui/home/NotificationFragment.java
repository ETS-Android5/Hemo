package com.dev334.blood.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev334.blood.R;
import com.dev334.blood.databinding.FragmentNotificationBinding;
import com.dev334.blood.model.Blood;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    public static NotificationFragment fragment=null;
    private View view;
    FragmentNotificationBinding binding;
    private BloodRequestAdapter bloodRequestAdapter;
    private List<Blood> bloods;
    private String TAG="NotificationFragLog";
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
        bloods=new ArrayList<>();
        bloods=((HomeActivity)getActivity()).getBloodRequests();
        Log.i(TAG, "onCreateView: "+bloods);
        bloodRequestAdapter= new BloodRequestAdapter(bloods);
        binding.recyclerNotification.setAdapter(bloodRequestAdapter);
        binding.recyclerNotification.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerNotification.setHasFixedSize(true);
        return binding.getRoot();
    }
}