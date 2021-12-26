package com.dev334.blood.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dev334.blood.R;
import com.dev334.blood.database.TinyDB;
import com.dev334.blood.databinding.FragmentScheduleBinding;
import com.dev334.blood.model.ApiResponse;
import com.dev334.blood.model.BloodBank;
import com.dev334.blood.model.Schedule;
import com.dev334.blood.ui.bank.BloodBankActivity;
import com.dev334.blood.util.retrofit.ApiClient;
import com.dev334.blood.util.retrofit.ApiInterface;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;

import org.joda.time.DateTime;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleFragment extends Fragment {

    public static ScheduleFragment fragment=null;
    private View view;
    private FragmentScheduleBinding binding;
    private String time="", dateString="";
    private BloodBank bloodBank;
    private String TAG="scheduleFragmentLog";

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
                dateString=date.getDay()+"-"+date.getMonth()+"-"+date.getYear();
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

        binding.bloodBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BloodBankActivity.class);
                startActivity(i);
            }
        });

        binding.bloodScheduleDone.setOnClickListener(v->{
            if(dateString.isEmpty()){
                Toast.makeText(getContext(), "Select a date", Toast.LENGTH_SHORT).show();
            }else if(time.isEmpty()){
                Toast.makeText(getContext(), "Select a time slot", Toast.LENGTH_SHORT).show();
            }else if(bloodBank==null){
                binding.bloodBank.setError("Select a blood bank");
            }else{
                scheduleAppointment();
            }
        });

        return binding.getRoot();
    }

    private void scheduleAppointment() {

        Schedule schedule=new Schedule(((HomeActivity)getActivity()).getUserId(), bloodBank.getId(),
                bloodBank.getBankName(), dateString, time);

        Log.i(TAG, "scheduleAppointment: "+schedule.getBank());


        Call<ApiResponse> call= ApiClient.getApiClient(getContext()).create(ApiInterface.class).schedule(schedule);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG, "onResponse: "+response.code());
                    Log.i(TAG, "onResponse: "+response.toString());
                    Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, "onResponse: "+response.body());
                if(response.body().getStatus()==200){
                    Log.i(TAG, "onResponse: Successful");
                    showdialog();
                }
            }

            private void showdialog() {

                final Dialog dialog=new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_appointment_scheduled);

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                clearAllfeilds();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private void clearAllfeilds() {
        disableAllButton();
        binding.bloodBank.setText("");
    }

    private void disableAllButton() {
        binding.btn9.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));
        binding.btn11.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));
        binding.btn13.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));
        binding.btn15.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));
    }

    @Override
    public void onResume() {
        super.onResume();
        TinyDB tinyDb =new TinyDB(getActivity());
        bloodBank=tinyDb.getObject("BloodBank", BloodBank.class);
        if(bloodBank!=null){
            binding.bloodBank.setText(bloodBank.getBankName());
        }
    }
}