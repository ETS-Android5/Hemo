package com.dev334.blood.ui.admin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev334.blood.R;
import com.dev334.blood.model.Blood;
import com.dev334.blood.util.app.AppConfig;
import com.dev334.blood.util.retrofit.ApiClient;
import com.dev334.blood.util.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivityRequest extends AppCompatActivity {

       private  String TAG="AdminActivityRequest";
       private BloodRequestAdminAdapter bloodRequestAdminAdapter;
       private List<Blood> pendingRequests;
       private AppConfig appConfig;
       private RecyclerView recyclerViewPendingRequests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pendingRequests=new ArrayList<>();
        recyclerViewPendingRequests=(RecyclerView) findViewById(R.id.blood_request_pending_recycler);
        reqPendingRequests();


        setContentView(R.layout.activity_admin_request);
    }

    private void reqPendingRequests() {

        appConfig=new AppConfig(this);
        Call<List<Blood>> call= ApiClient.getApiClient(getApplicationContext()).create(ApiInterface.class).getAdminRequests(appConfig.getUserLocation());
        call.enqueue(new Callback<List<Blood>>() {
            @Override
            public void onResponse(Call<List<Blood>> call, Response<List<Blood>> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG, "onResponse: "+response.code());
                    Log.i(TAG, "onResponse: "+response.toString());
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(response.code() == 200){
                    Log.i(TAG, "onResponse: Successful");
                    pendingRequests=response.body();
                    Log.i(TAG, "Array of Pending Requests: "+pendingRequests);
                    bloodRequestAdminAdapter= new BloodRequestAdminAdapter(pendingRequests,getApplicationContext());
                    recyclerViewPendingRequests.setAdapter(bloodRequestAdminAdapter);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerViewPendingRequests.setLayoutManager(mLayoutManager);
                    recyclerViewPendingRequests.setHasFixedSize(true);

                }
            }

            @Override
            public void onFailure(Call<List<Blood>> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });


    }
}