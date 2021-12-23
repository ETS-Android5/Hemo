package com.dev334.blood.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev334.blood.R;
import com.dev334.blood.databinding.FragmentScheduleBinding;
import com.dev334.blood.ui.BloodBankActivity;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;

import org.joda.time.DateTime;

import java.util.Date;

public class ScheduleFragment extends Fragment {

    public static ScheduleFragment fragment=null;
    private View view;
    private FragmentScheduleBinding binding;
    private String time, date, bloodBank;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    public static ScheduleFragment newInstance() {
        if(fragment==null) {
            fragment = new ScheduleFragment();
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
        binding = FragmentScheduleBinding.inflate(getLayoutInflater());
        DateTime start=DateTime.now();
        DateTime end = DateTime.now().plusDays(10);
        binding.datePicker.setStartDate(start.getDayOfMonth(), start.getMonthOfYear(), start.getYear());
        binding.datePicker.setEndDate(end.getDayOfMonth(), end.getMonthOfYear(), end.getYear());

        binding.datePicker.getSelectedDate(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@Nullable Date date) {

            }
        });

        binding.btn9.setOnClickListener(v->{
            disableAllButton();
            binding.btn9.setBackground(getResources().getDrawable(R.drawable.primary_color_filled));
            time=binding.btn9.getText().toString();
        });

        binding.btn11.setOnClickListener(v->{
            disableAllButton();
            binding.btn11.setBackground(getResources().getDrawable(R.drawable.primary_color_filled));
            time=binding.btn11.getText().toString();
        });

        binding.btn13.setOnClickListener(v->{
            disableAllButton();
            binding.btn13.setBackground(getResources().getDrawable(R.drawable.primary_color_filled));
            time=binding.btn13.getText().toString();
        });

        binding.btn15.setOnClickListener(v->{
            disableAllButton();
            binding.btn15.setBackground(getResources().getDrawable(R.drawable.primary_color_filled));
            time=binding.btn15.getText().toString();
        });

        binding.bloodScheduleDone.setOnClickListener(v->{
            Intent i = new Intent(getActivity(), BloodBankActivity.class);
            startActivity(i);
        });

        return binding.getRoot();
    }

    private void disableAllButton() {
        binding.btn9.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));
        binding.btn11.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));
        binding.btn13.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));
        binding.btn15.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));
    }

}