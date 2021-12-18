package com.dev334.blood.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dev334.blood.R;
import com.dev334.blood.model.ApiResponse;
import com.dev334.blood.model.Blood;
import com.dev334.blood.model.User;
import com.dev334.blood.util.retrofit.ApiClient;
import com.dev334.blood.util.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestFragment extends Fragment {

    public static RequestFragment fragment=null;
    private View view;
    private String TAG="RequestFragment";

    public RequestFragment() {
        // Required empty public constructor
    }

    public static RequestFragment newInstance() {
        if(fragment==null) {
            fragment = new RequestFragment();
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
        view = inflater.inflate(R.layout.fragment_request, container, false);
        reqBlood();
        return view;
    }

    private void reqBlood() {
      Blood blood = new Blood("61bafacadaecb4894fa11447",77.1244558,76.222666,"Lucknow",20,"A+");
      Call<ApiResponse> call= ApiClient.getApiClient(getContext()).create(ApiInterface.class).reqBlood(blood);
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
                }
          }

          @Override
          public void onFailure(Call<ApiResponse> call, Throwable t) {
              Log.i(TAG, "onFailure: "+t.getMessage());
          }
      });
    }

}