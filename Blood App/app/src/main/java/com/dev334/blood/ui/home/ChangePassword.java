package com.dev334.blood.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dev334.blood.R;
import com.dev334.blood.databinding.ActivityChangePasswordBinding;

public class ChangePassword extends AppCompatActivity {
    private Button changePasswordBtn;
    private EditText passwordEditText, RePasswordEditText;
    ActivityChangePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityChangePasswordBinding.inflate(getLayoutInflater());

        binding.resetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Pass= String.valueOf(binding.resetPasswordNew.getText().toString());
                String cPass= String.valueOf(binding.resetPasswordConfirm.getText().toString());

                if(Pass.isEmpty()){
                    binding.resetPasswordNew.setError("Your password can't be empty");
                }
                else if(Pass.equals(cPass)){
                    binding.resetPasswordConfirm.setError("Your new password can't be same as old one");
                }
                else{

                }
            }
        });
        setContentView(binding.getRoot());
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}