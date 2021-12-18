package com.dev334.blood.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dev334.blood.R;
import com.dev334.blood.databinding.FragmentRequestBinding;
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
    private FragmentRequestBinding binding;
    private String blood,quantity,info;

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
        binding=FragmentRequestBinding.inflate(getLayoutInflater());

        disableAllButton();

        binding.button1.setOnClickListener(v->{
            blood=binding.button1.getText().toString();
            disableAllButton();
            binding.button1.setBackground(getResources().getDrawable(R.drawable.primary_color_filled));
        });

        binding.button2.setOnClickListener(v->{
            blood=binding.button2.getText().toString();
            disableAllButton();
            binding.button2.setBackground(getResources().getDrawable(R.drawable.primary_color_filled));
        });

        binding.button3.setOnClickListener(v->{
            blood=binding.button3.getText().toString();
            disableAllButton();
            binding.button3.setBackground(getResources().getDrawable(R.drawable.primary_color_filled));
        });

        binding.button4.setOnClickListener(v->{
            blood=binding.button4.getText().toString();
            disableAllButton();
            binding.button4.setBackground(getResources().getDrawable(R.drawable.primary_color_filled));
        });

        binding.button5.setOnClickListener(v->{
            blood=binding.button5.getText().toString();
            disableAllButton();
            binding.button5.setBackground(getResources().getDrawable(R.drawable.primary_color_filled));
        });

        binding.button6.setOnClickListener(v->{
            blood=binding.button6.getText().toString();
            disableAllButton();
            binding.button6.setBackground(getResources().getDrawable(R.drawable.primary_color_filled));
        });

        binding.button7.setOnClickListener(v->{
            blood=binding.button7.getText().toString();
            disableAllButton();
            binding.button7.setBackground(getResources().getDrawable(R.drawable.primary_color_filled));
        });

        binding.button8.setOnClickListener(v->{
            blood=binding.button8.getText().toString();
            disableAllButton();
            binding.button8.setBackground(getResources().getDrawable(R.drawable.primary_color_filled));
        });


        binding.buttonLocationNext.setOnClickListener(v->{
            info=binding.editInformation.getText().toString();
            quantity=binding.EditQuantity.getText().toString();

            if(quantity.isEmpty()){
                binding.EditQuantity.setError("Enter quantity");
            }else if(blood.isEmpty()){
                Toast.makeText(getContext(), "Select a blood type", Toast.LENGTH_SHORT).show();
            }else{
                //open location
            }

        });


        return binding.getRoot();
    }

    private void disableAllButton() {
        binding.button1.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));
        binding.button2.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));
        binding.button3.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));
        binding.button4.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));
        binding.button5.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));
        binding.button6.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));
        binding.button7.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));
        binding.button8.setBackground(getResources().getDrawable(R.drawable.primary_grey_filled));

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