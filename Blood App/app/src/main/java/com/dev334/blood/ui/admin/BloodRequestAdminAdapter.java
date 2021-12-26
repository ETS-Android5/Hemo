package com.dev334.blood.ui.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev334.blood.R;
import com.dev334.blood.model.Blood;

import java.util.List;

public class BloodRequestAdminAdapter extends RecyclerView.Adapter<BloodRequestAdminAdapter.mViewHolder> {
    private List<Blood> bloods;

    private Context context;
    private String TAG="BloodRequestAdminAdapter";
    public BloodRequestAdminAdapter(List<Blood> bloods, Context context) {
        this.bloods = bloods;

        this.context = context;

    }



    @NonNull
    @Override
    public BloodRequestAdminAdapter.mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_blood_admin, parent, false);
        return new BloodRequestAdminAdapter.mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BloodRequestAdminAdapter.mViewHolder holder, int position) {
        holder.setItems(bloods.get(position).getName(),bloods.get(position).getQuantity(),bloods.get(position).getBlood());


    }



    @Override
    public int getItemCount() {
        return bloods.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxtView,bloodUnitTxtView,bloodGrpTxtView;
        ImageView locationImgView,documentImgView,callImgView;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTxtView=itemView.findViewById(R.id.request_name);
            bloodGrpTxtView=itemView.findViewById(R.id.blood_group);
            bloodUnitTxtView=itemView.findViewById(R.id.blood_quantity);
            callImgView=itemView.findViewById(R.id.contact_icon);
            locationImgView=itemView.findViewById(R.id.view_location_icon);
            documentImgView=itemView.findViewById(R.id.view_doc_icon);



        }

        public void setItems(String user, Integer quantity, String blood) {
            nameTxtView.setText(user);
            bloodGrpTxtView.setText(blood);
            bloodUnitTxtView.setText(quantity.toString()+" units required");
        }
    }
}
