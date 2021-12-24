package com.dev334.blood.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev334.blood.R;
import com.dev334.blood.model.Blood;
import com.dev334.blood.model.Schedule;

import java.util.List;

public class ScheduleRequestAdapter extends RecyclerView.Adapter<ScheduleRequestAdapter.mViewHolder>{

    private List<Schedule> schedules;
    public ScheduleRequestAdapter(List<Schedule> schedules){
        this.schedules=schedules;
    }

    @NonNull
    @Override
    public ScheduleRequestAdapter.mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_card, parent, false);
        return new ScheduleRequestAdapter.mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleRequestAdapter.mViewHolder holder, int position) {
       holder.setItems(schedules.get(position).getUser(),schedules.get(position).getBank(),schedules.get(position).getDate(),schedules.get(position).getTime());
        holder.approvedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), "Marked Approved",Toast.LENGTH_LONG).show();
            }
        });

        holder.declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int actualPosition=holder.getAdapterPosition();
                schedules.remove(actualPosition);
                notifyItemRemoved(actualPosition);
                notifyItemRangeChanged(actualPosition,schedules.size());
                Toast.makeText(view.getContext(), "Declined",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder{

        TextView userNameTxt,bankTxt,dateTxt,timeTxt;
        Button approvedBtn,declineBtn;
        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTxt=itemView.findViewById(R.id.textViewUserName);
            bankTxt=itemView.findViewById(R.id.textViewLocation);
            dateTxt=itemView.findViewById(R.id.textViewDate);
            timeTxt=itemView.findViewById(R.id.textViewTiming);
            approvedBtn=itemView.findViewById(R.id.buttonMarkApp);
            declineBtn=itemView.findViewById(R.id.buttonDecline);


        }

        public void setItems(String user, String bank, String date, String time) {
            userNameTxt.setText(user);
            bankTxt.setText(bank);
            dateTxt.setText(date);
            timeTxt.setText(time);
        }
    }
}
