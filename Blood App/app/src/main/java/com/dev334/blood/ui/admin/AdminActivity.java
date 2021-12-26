package com.dev334.blood.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dev334.blood.databinding.ActivityAdminBinding;
import com.dev334.blood.model.Schedule;
import com.dev334.blood.ui.admin.ScheduleRequestAdapter;
import com.dev334.blood.util.retrofit.ApiClient;
import com.dev334.blood.util.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {

    private String TAG="AdminActivity";
    private ScheduleRequestAdapter scheduleRequestAdapter;
    private List<Schedule> approvedSchedules,pendingSchedule;
    ActivityAdminBinding binding;
    private boolean PENDING=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityAdminBinding.inflate(getLayoutInflater());
        approvedSchedules =new ArrayList<>();
        pendingSchedule = new ArrayList<>();
        reqPendingSchedule();
        reqApprovedSchedule();


        binding.buttonPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PENDING=true;
                scheduleRequestAdapter= new ScheduleRequestAdapter(pendingSchedule,PENDING,getApplicationContext());
                binding.ScheduleRecyclerView.setAdapter(scheduleRequestAdapter);
                binding.ScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.ScheduleRecyclerView.setHasFixedSize(true);
            }
        });

        binding.buttonApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PENDING=false;
                scheduleRequestAdapter= new ScheduleRequestAdapter(approvedSchedules,PENDING,getApplicationContext());
                binding.ScheduleRecyclerView.setAdapter(scheduleRequestAdapter);
                binding.ScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.ScheduleRecyclerView.setHasFixedSize(true);
            }
        });

        setContentView(binding.getRoot());
    }

    private void reqApprovedSchedule() {
        Call<List<Schedule>> call= ApiClient.getApiClient(getApplicationContext()).create(ApiInterface.class).getSchedule("2507","0");
        call.enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG, "onResponse: "+response.code());
                    Log.i(TAG, "onResponse: "+response.toString());
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(response.code() == 200){
                    Log.i(TAG, "onResponse: Successful");
                    approvedSchedules=response.body();
                    Log.i(TAG, "Array of Schedules: "+approvedSchedules);

                }
            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private void reqPendingSchedule() {

        Call<List<Schedule>> call= ApiClient.getApiClient(getApplicationContext()).create(ApiInterface.class).getSchedule("2507","1");
        call.enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG, "onResponse: "+response.code());
                    Log.i(TAG, "onResponse: "+response.toString());
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(response.code() == 200){
                    Log.i(TAG, "onResponse: Successful");
                    pendingSchedule=response.body();
                    Log.i(TAG, "Array of Schedules: "+pendingSchedule);
                    scheduleRequestAdapter= new ScheduleRequestAdapter(pendingSchedule,PENDING,getApplicationContext());
                    binding.ScheduleRecyclerView.setAdapter(scheduleRequestAdapter);
                    binding.ScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    binding.ScheduleRecyclerView.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}