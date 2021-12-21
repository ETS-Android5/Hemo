package com.dev334.blood.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev334.blood.R;
import com.dev334.blood.model.Blood;

import java.util.List;

public class BloodRequestAdapter extends RecyclerView.Adapter<BloodRequestAdapter.mViewHolder>{

    private List<Blood> bloods;
    public BloodRequestAdapter(List<Blood> bloods){
        this.bloods=bloods;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_request_card, parent, false);
        return new BloodRequestAdapter.mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return bloods.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder{

        TextView nameTxtView,bloodUnitTxtView,bloodGrpTxtView,locationTxtView;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxtView=itemView.findViewById(R.id.request_card_name);
            bloodGrpTxtView=itemView.findViewById(R.id.request_card_blood_group);
            bloodUnitTxtView=itemView.findViewById(R.id.request_card_blood_quantity);
            locationTxtView=itemView.findViewById(R.id.request_card_location);
        }

    }
}
