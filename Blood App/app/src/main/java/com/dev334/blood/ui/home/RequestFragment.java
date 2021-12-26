package com.dev334.blood.ui.home;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.dev334.blood.R;
import com.dev334.blood.database.TinyDB;
import com.dev334.blood.databinding.FragmentRequestBinding;
import com.dev334.blood.model.ApiResponse;
import com.dev334.blood.model.Blood;
import com.dev334.blood.util.app.AppConfig;
import com.dev334.blood.util.retrofit.ApiClient;
import com.dev334.blood.util.retrofit.ApiInterface;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestFragment extends Fragment {

    public static RequestFragment fragment=null;
    private View view;
    private String TAG="RequestFragment";
    private FragmentRequestBinding binding;
    private String blood,quantity,info;
    private Blood mBlood;
    private TinyDB tinyDB;
    private int REQ_PDF=21;
    private String encodedPDF;
    private boolean pdfUploadFlag=false;
    private boolean CONTACT_UPLOAD_FLAG=false;
    private static final int CONTACT_PERMISSION_CODE=1;
    private static final int CONTACT_PICK_CODE=2;
    private AppConfig appConfig;

    StorageReference storageReference;


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

        mBlood = new Blood();
        tinyDB=new TinyDB(getContext());
        appConfig=new AppConfig(getContext());
        storageReference= FirebaseStorage.getInstance().getReference();

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


        binding.verifyFile.setOnClickListener(v->{
             Intent chooseFile=new Intent(Intent.ACTION_GET_CONTENT);
             chooseFile.setType("application/pdf");
             chooseFile=Intent.createChooser(chooseFile,"Choose a valid pdf");
             startActivityForResult(chooseFile,REQ_PDF);
        });

        binding.phoneNo.setOnClickListener(v->{

                if(checkContactPermission())
                {
                  pickContactIntent();
                }
                else
                {
                    requestContactPermission();
                }

        });



        binding.buttonLocationNext.setOnClickListener(v->{

            if(binding.buttonLocationNext.getText().toString().equals("Done")){
                reqBlood();
                binding.buttonLocationNext.setText("Next");
                return;
            }

            info=binding.editInformation.getText().toString();
            quantity=binding.EditQuantity.getText().toString();

            if(quantity.isEmpty()){
                binding.EditQuantity.setError("Enter quantity");
            }else if(blood.isEmpty()){
                Toast.makeText(getContext(), "Select a blood type", Toast.LENGTH_SHORT).show();
            }
            else if(pdfUploadFlag==false)
            {
               binding.verifyFile.setError("Upload a valid document to avoid illegal activities");
            }
            else if(CONTACT_UPLOAD_FLAG==false)
            {
                binding.phoneNo.setError("please give contact number");
            }
            else{
                //open location
                mBlood.setBlood(blood);
                mBlood.setQuantity(Integer.parseInt(quantity));
                mBlood.setInfo(info);
                mBlood.setLocation(appConfig.getUserLocation());
                mBlood.setUser(appConfig.getUserID());
                ((HomeActivity)getActivity()).openMapActivity();
            }

        });
        return binding.getRoot();
    }



    private void uploadToStorage(Uri data, Intent intent) {

         final ProgressDialog progressDialog=new ProgressDialog(getContext());
         progressDialog.setTitle("Loading...");
         progressDialog.show();

         StorageReference reference=storageReference.child("BloodRequest"+System.currentTimeMillis()+".pdf");

         reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                 Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                 while (!uriTask.isComplete());
                 Uri uri=uriTask.getResult();

                 mBlood.setFile(uri.toString());
                 File file= new File(uri.getPath());


                 binding.verifyFile.setText(file.getName().toString());
                 pdfUploadFlag=true;
                 progressDialog.dismiss();


             }
         }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                 double progress=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                 progressDialog.setMessage("File Uploading ..."+(int)progress);

             }
         });


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
      Call<ApiResponse> call= ApiClient.getApiClient(getContext()).create(ApiInterface.class).reqBlood(mBlood);
      call.enqueue(new Callback<ApiResponse>() {
          @Override
          public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
              if(!response.isSuccessful()){
                    Log.i(TAG, "onResponse: "+response.code());
                    Log.i(TAG, "onResponse: "+response.toString());
                    Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                    showErrorDialog();
                    return;
                }
                Log.i(TAG, "onResponse: "+response.body());
                if(response.body().getStatus()==200){
                    Log.i(TAG, "onResponse: Successful");
                    showDialog();
                    clearAllFeilds();
                }
          }

          @Override
          public void onFailure(Call<ApiResponse> call, Throwable t) {
              showErrorDialog();
          }
      });
    }

    private void showErrorDialog() {

        final Dialog dialog=new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_error_404);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        Button goToHome=dialog.findViewById(R.id.go_to_home1);
        goToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity)getActivity()).openHomeFragment();
                dialog.dismiss();
            }
        });

    }

    private void clearAllFeilds() {
        disableAllButton();
        binding.EditQuantity.setText("");
        binding.editInformation.setText("");
        binding.verifyFile.setText("Upload Valid document");
    }

    private void showDialog() {

        final Dialog dialog=new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_blood_requested);
        Button goToHome=dialog.findViewById(R.id.go_to_home);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        goToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity)getActivity()).openHomeFragment();
                dialog.dismiss();
            }

        });

    }

    private boolean checkContactPermission(){

         boolean result= ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)== (PackageManager.PERMISSION_GRANTED);

         return result;
    }

    private void requestContactPermission(){
          String[] permission={Manifest.permission.READ_CONTACTS};
        ActivityCompat.requestPermissions(getActivity(),permission,CONTACT_PERMISSION_CODE);
    }

    private void pickContactIntent(){
          Intent intent=new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
          startActivityForResult(intent,CONTACT_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==CONTACT_PERMISSION_CODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                pickContactIntent();
            }
            else
            {
                Toast.makeText(getContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_PDF && resultCode==RESULT_OK && data!=null)
        {
            uploadToStorage(data.getData(),data);
        }

        if(requestCode==CONTACT_PICK_CODE && resultCode==RESULT_OK)
        {
            binding.phoneNo.setText("");


            Cursor c = getActivity().getContentResolver().query(data.getData(), null, null, null, null);
            String number="";
            if (c.moveToFirst()) {


                String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                String hasPhone =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                //String hasEmail =c.getString(c.getColumnIndex(ContactsContract.Contacts.Data.DATA1.));


                if (hasPhone.equalsIgnoreCase("1")) {
                    Cursor phones = getActivity().getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                            null, null);
                    phones.moveToFirst();
                    number = phones.getString(phones.getColumnIndexOrThrow("data1"));
                    binding.phoneNo.setText(number);
                    mBlood.setContact(number);
                    CONTACT_UPLOAD_FLAG=true;
                    phones.close();
                }


                c.close();
            }


        }

    }


    @Override
    public void onResume() {
        super.onResume();
        mBlood.setLatitude(tinyDB.getDouble("Latitude"));
        mBlood.setLongitude(tinyDB.getDouble("Longitude"));
        tinyDB.putDouble("Latitude", 0);
        tinyDB.putDouble("Longitude", 0);
        if(mBlood.getLatitude()!=0){
            binding.buttonLocationNext.setText("Done");
        }
    }
}