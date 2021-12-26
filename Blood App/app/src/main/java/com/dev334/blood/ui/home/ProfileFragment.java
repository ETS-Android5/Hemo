package com.dev334.blood.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.dev334.blood.R;
import com.dev334.blood.databinding.FragmentProfileBinding;
import com.dev334.blood.model.ApiResponse;
import com.dev334.blood.model.Blood;
import com.dev334.blood.model.Schedule;
import com.dev334.blood.ui.admin.AdminActivity;
import com.dev334.blood.ui.admin.AdminActivityRequest;
import com.dev334.blood.ui.login.LoginActivity;
import com.dev334.blood.util.app.AppConfig;
import com.dev334.blood.util.retrofit.ApiClient;
import com.dev334.blood.util.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    public static ProfileFragment fragment=null;
    private View view;
    FragmentProfileBinding binding;
    private String TAG="ProfileFragment";
    private AppConfig appConfig;
    private Blood myRequest;
    private Schedule mySchedule;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        if(fragment==null) {
            fragment = new ProfileFragment();
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
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        appConfig = new AppConfig(getContext());
        binding.settingsChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ChangePassword.class);
                startActivity(i);
            }
        });

        binding.settingsFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), UserFeedback.class);
                startActivity(i);
            }
        });

        binding.settingsAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                View view=getLayoutInflater().inflate(R.layout.contact_us_dailog,null);
                Button contact = view.findViewById(R.id.contact_btn);
                alert.setView(view);
                AlertDialog show=alert.show();

                contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("plain/text");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "oneon334@gmail.com" });
                        intent.putExtra(Intent.EXTRA_SUBJECT, "LiteLo-Contact Us");
                        startActivity(Intent.createChooser(intent, "Send us email"));
                    }
                });

                alert.setCancelable(true);
                show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });

        binding.settingsShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Download Hemo App");
                i.putExtra(Intent.EXTRA_TEXT, "Download Hemo App \n https://play.google.com/store/apps/details?id=com.dev334.litelo");
                startActivity(Intent.createChooser(i, "Share app"));
            }
        });

        binding.settingsLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appConfig.setAuthToken("");
                appConfig.setLoginStatus(false);
                appConfig.setProfileCreated(false);
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        binding.settingsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                View view=getLayoutInflater().inflate(R.layout.dialog_delete_account,null);
                TextView delete=view.findViewById(R.id.dDeleteAcc);
                TextView cancel=view.findViewById(R.id.dCancel);
                alert.setView(view);
                AlertDialog show=alert.show();
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show.dismiss();
                    }
                });

                alert.setCancelable(true);
                show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });

        binding.settingsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent =new Intent(getActivity(), AdminActivity.class);
               startActivity(intent);
            }
        });

        binding.textViewYourAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserSchedule();
            }
        });

        binding.settingsAdminRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AdminActivityRequest.class);
                startActivity((intent));
            }
        });

        binding.textViewYourRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserRequest();
            }
        });
        return binding.getRoot();
    }

    private void showDailog() {
        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
        View view=getLayoutInflater().inflate(R.layout.my_appointment_dailog,null);
        alert.setView(view);
        AlertDialog show=alert.show();

        TextView name=view.findViewById(R.id.appointmentUserName);
        TextView hospital=view.findViewById(R.id.appointmentLocation);
        TextView date=view.findViewById(R.id.appointmentDate);
        TextView timing=view.findViewById(R.id.appointmentTiming);
        Button cancel=view.findViewById(R.id.cancel_app_button);

        name.setText(mySchedule.getName());
        hospital.setText(mySchedule.getBank());
        date.setText(mySchedule.getDate());
        timing.setText(mySchedule.getTime());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeUserSchedule();
                show.dismiss();
            }
        });

        alert.setCancelable(true);
        show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void getUserRequest(){
        Call<Blood> call = ApiClient.getApiClient(getContext()).create(ApiInterface.class)
                .getUserRequest(appConfig.getUserID());
        call.enqueue(new Callback<Blood>() {
            @Override
            public void onResponse(Call<Blood> call, Response<Blood> response) {
                if(!response.isSuccessful()){
                    if(response.code()==404){
                        Toast.makeText(getContext(), "No schedule", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.i(TAG, "onResponse:Error "+response.message());
                    }
                    return;
                }

                if(response.code()==200){
                    Log.i(TAG, "onResponse: "+response.body());
                    myRequest=response.body();
                    showDailogRequest();
                }

            }

            @Override
            public void onFailure(Call<Blood> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private void showDailogRequest() {
        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
        View view=getLayoutInflater().inflate(R.layout.my_request_dailog,null);
        alert.setView(view);
        AlertDialog show=alert.show();

        TextView name=view.findViewById(R.id.request_name_dailog);
        TextView BloodQty=view.findViewById(R.id.blood_dailog_quantity);
        TextView BloodGrp=view.findViewById(R.id.blood_group_dailog);
        ImageView file=view.findViewById(R.id.view_doc_icon_dailog);
        ImageView location=view.findViewById(R.id.view_location_icon_dailog);
        Button cancelReq=view.findViewById(R.id.btn_cancel_request_dailog);

        name.setText(myRequest.getName());
        BloodQty.setText(myRequest.getQuantity() + " Units");
        BloodGrp.setText(myRequest.getBlood());



        cancelReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeUserRequest();
                show.dismiss();
            }
        });

        alert.setCancelable(true);
        show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void getUserSchedule(){
        Call<Schedule> call = ApiClient.getApiClient(getContext()).create(ApiInterface.class)
                .getUserSchedule(appConfig.getUserID());
        call.enqueue(new Callback<Schedule>() {
            @Override
            public void onResponse(Call<Schedule> call, Response<Schedule> response) {
                if(!response.isSuccessful()){
                    if(response.code()==404){
                        Toast.makeText(getContext(), "No donation Appointment", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.i(TAG, "onResponse:Error "+response.message());
                    }
                    return;
                }

                if(response.code()==200){
                    Log.i(TAG, "onResponse: "+response.body());
                    mySchedule=response.body();
                    showDailog();
                }

            }

            @Override
            public void onFailure(Call<Schedule> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    public void removeUserRequest(){
        Call<ApiResponse> call= ApiClient.getApiClient(getContext()).create(ApiInterface.class)
                .removeRequest(appConfig.getUserID(), myRequest.get_id());
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
                    Toast.makeText(getContext(), "Removed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    public void removeUserSchedule(){
        Call<ApiResponse> call= ApiClient.getApiClient(getContext()).create(ApiInterface.class)
                .removeSchedule(appConfig.getUserID(), mySchedule.get_id());
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
                    Toast.makeText(getContext(), "Removed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

}