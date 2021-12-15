package com.dev334.blood.ui.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.dev334.blood.database.TinyDB;
import com.dev334.blood.R;

import java.util.ArrayList;

public class CreateProfileFragment extends Fragment {

    private View view;
    private TinyDB tinyDB;
    private String PhoneNo,Username,Organisation,Facebook, Instagram;
    private TextView EditName,EditFB,EditInsta,EditInterest;
    private TextView EditOrg;
    private Button btnCreate;
    private ArrayList<String> Organisations,userInterest;
    private static String TAG="CreateProfile";
    private String uInterest;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_profile, container, false);

        EditName=view.findViewById(R.id.EditName);
        EditOrg=view.findViewById(R.id.EditOrg);
        btnCreate=view.findViewById(R.id.btnCreate);


        Organisations=new ArrayList<>();
        userInterest=new ArrayList<>();
        tinyDB=new TinyDB(getContext());
        userInterest=tinyDB.getListString("UserInterest");
        PhoneNo=tinyDB.getString("PhoneNumber");

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, Organisations);

        EditOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        btnCreate.setOnClickListener(v -> {
            Username=EditName.getText().toString();
            Organisation=EditOrg.getText().toString();
            Facebook=EditFB.getText().toString();
            Instagram=EditInsta.getText().toString();

            if(check()){
                //create profile
            }
        });
        
        return view;
    }



    private boolean check() {
        if(Username.isEmpty()){
            return false;
        }else if(Organisation.isEmpty()){
            return false;
        }else{
            if(Organisations.contains(Organisation)){
                if(!Instagram.isEmpty()){
                    if(!URLUtil.isValidUrl(Instagram)){
                        EditInsta.setError("Enter valid URL or else leave empty");
                        return false;
                    }else{
                        if(!Instagram.contains("instagram") || !Instagram.toLowerCase().contains("instagram")){
                            EditInsta.setError("Enter valid URL or else leave empty");
                            return false;
                        }
                    }
                }
                if(!Facebook.isEmpty()){
                    if(!URLUtil.isValidUrl(Facebook)){
                        EditFB.setError("Enter valid URL or else leave empty");
                        return false;
                    }else{
                        if(!Facebook.contains("facebook") || !Facebook.toLowerCase().contains("facebook")){
                            EditFB.setError("Enter valid URL or else leave empty");
                            return false;
                        }
                    }
                }
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EditOrg.setText(tinyDB.getString("Organisation"));
        userInterest=tinyDB.getListString("UserInterest");
    }
    
}