package com.dev334.blood.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dev334.blood.R;
import com.dev334.blood.databinding.FragmentProfileBinding;
import com.dev334.blood.ui.login.LoginActivity;
import com.dev334.blood.ui.login.LoginFragment;
import com.dev334.blood.util.app.AppConfig;

public class ProfileFragment extends Fragment {

    public static ProfileFragment fragment=null;
    private View view;
    FragmentProfileBinding binding;
    private String TAG="ProfileFragment";
    private AppConfig appConfig;

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
        return binding.getRoot();
    }
}