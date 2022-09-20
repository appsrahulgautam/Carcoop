package com.carcoop.helpme.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.carcoop.helpme.Activity.ShowPreviousEmergencydetail;
import com.carcoop.helpme.Constance;
import com.carcoop.helpme.R;
import com.carcoop.helpme.interfaces.HistoryDeleteOnclicklisterner;
import com.carcoop.helpme.pojos.Emergency_detailPOJO;
import com.carcoop.helpme.utils.TimeUtils;

import java.util.ArrayList;

public class EmergencyList_adapter extends RecyclerView.Adapter<EmergencyList_adapter.EmergencyDetailHolder> {

    private static final String TAG = "EmergencyList_adapter";
    private ArrayList<Emergency_detailPOJO> emergency_detailPOJOS;
    private Context context;
    private Dialog dialog;
    private HistoryDeleteOnclicklisterner historyDeleteOnclicklisterner;

    public EmergencyList_adapter(Context context, ArrayList<Emergency_detailPOJO> emergency_detailPOJOS) {
        this.context = context;
        this.emergency_detailPOJOS = emergency_detailPOJOS;
    }

    public void setdeleteHistoryListener(HistoryDeleteOnclicklisterner historyDeleteOnclicklisterner) {
        this.historyDeleteOnclicklisterner = historyDeleteOnclicklisterner;

    }

    @NonNull
    @Override
    public EmergencyDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmergencyDetailHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.emergency_lists_layout, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull EmergencyDetailHolder holder, int position) {
        Emergency_detailPOJO emergency_detailPOJO = emergency_detailPOJOS.get(position);

        holder.date_emp_tv.setText(TimeUtils.covertTimeToText(emergency_detailPOJO.getTimeandDatestamp()));
        holder.vehicleRegno_tv.setText(emergency_detailPOJO.getVehicleRegno());
        holder.carbrand_tv.setText(emergency_detailPOJO.getCarBrand());
        holder.carrmodel_tv.setText(emergency_detailPOJO.getCarmodel());

        setondeletelistener(holder, position);

    }

    private void setondeletelistener(EmergencyDetailHolder holder, int position) {
        holder.deleteHistory_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                emergency_detailPOJOS.remove(position);
//                notifyItemChanged(position);
//                historyDeleteOnclicklisterner.onhistorydelete(position);
//                Log.e(TAG, "onClick: "+position+ " arraylist-> "+emergency_detailPOJOS.get(position));
                showCustomDialog(position);
            }
        });


        holder.topCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowPreviousEmergencydetail.class);
                intent.putExtra(Constance.PRV_EMP_DETAIL, emergency_detailPOJOS.get(position));
                intent.putExtra("position", position);
                context.startActivity(intent);
//
//                ShowPreviousEmergencydetail.StartActivity((Activity) context,);
            }
        });


    }

    public int getsize() {
        return emergency_detailPOJOS.size();
    }

    @Override
    public int getItemCount() {
        return emergency_detailPOJOS.size();
    }

    public void showCustomDialog(int position) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.request_submitted_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        FrameLayout frmOk = dialog.findViewById(R.id.frmOk);
        FrameLayout frmNo = dialog.findViewById(R.id.frmNo);

        frmNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        frmOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: data position-> " + position);

                emergency_detailPOJOS.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                historyDeleteOnclicklisterner.onhistorydelete(position);
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    static class EmergencyDetailHolder extends RecyclerView.ViewHolder {
        ConstraintLayout root;
        CardView bottomCard, topCard;
        ImageView deleteHistory_IV, moreoption;

        TextView date_emp_tv, vehicleRegno_tv, carbrand_tv, carrmodel_tv;

        public EmergencyDetailHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.root);
            moreoption = itemView.findViewById(R.id.moreoption);
//            bottomCard = itemView.findViewById(R.id.bottomCard);
            topCard = itemView.findViewById(R.id.topCard);
            deleteHistory_IV = itemView.findViewById(R.id.deleteHistory_IV);
            date_emp_tv = itemView.findViewById(R.id.date_emp_tv);
            vehicleRegno_tv = itemView.findViewById(R.id.vehicleRegno_tv);
            carbrand_tv = itemView.findViewById(R.id.carbrand_tv);
            carrmodel_tv = itemView.findViewById(R.id.carrmodel_tv);

        }
    }
}
