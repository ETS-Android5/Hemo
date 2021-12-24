package com.dev334.blood.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dev334.blood.R;
import com.dev334.blood.databinding.ActivityAdminBinding;
import com.dev334.blood.databinding.ActivityHomeBinding;
import com.dev334.blood.model.ApiResponse;
import com.dev334.blood.model.Schedule;
import com.dev334.blood.ui.home.BloodRequestAdapter;
import com.dev334.blood.ui.home.HomeActivity;
import com.dev334.blood.ui.home.ScheduleRequestAdapter;
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
    private List<Schedule> schedules;
    private List<Schedule> ApprovedSchedules;
    ActivityAdminBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityAdminBinding.inflate(getLayoutInflater());
        schedules=new ArrayList<>();
        reqSchedule();

        binding.buttonPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reqSchedule();
            }
        });

        binding.buttonApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        setContentView(binding.getRoot());
    }
    private void reqSchedule() {

        Call<List<Schedule>> call= ApiClient.getApiClient(getApplicationContext()).create(ApiInterface.class).getSchedule("2525","1");
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
                    schedules=response.body();
                    Log.i(TAG, "Array of Schedules: "+schedules);
                    scheduleRequestAdapter= new ScheduleRequestAdapter(schedules);
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