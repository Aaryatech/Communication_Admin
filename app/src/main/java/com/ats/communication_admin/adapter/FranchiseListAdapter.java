package com.ats.communication_admin.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.communication_admin.R;
import com.ats.communication_admin.activity.AFEIVisitActivity;
import com.ats.communication_admin.activity.AfeDateWiseReportActivity;
import com.ats.communication_admin.activity.QuestionsActivity;
import com.ats.communication_admin.bean.AfeDateWiseReport;
import com.ats.communication_admin.bean.FranchiseeList;
import com.ats.communication_admin.bean.Message;
import com.ats.communication_admin.constants.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MAXADMIN on 19/3/2018.
 */

public class FranchiseListAdapter extends RecyclerView.Adapter<FranchiseListAdapter.MyViewHolder> {

    private ArrayList<FranchiseeList> franchiseeList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, owner, mobile, address;
        public ImageView ivImage;
        public LinearLayout llBack;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvFrList_Name);
            owner = view.findViewById(R.id.tvFrList_Owner);
            mobile = view.findViewById(R.id.tvFrList_Mobile);
            address = view.findViewById(R.id.tvFrList_Address);
            llBack = view.findViewById(R.id.llFrList);
            ivImage = view.findViewById(R.id.ivFrList_Image);
        }
    }


    public FranchiseListAdapter(ArrayList<FranchiseeList> franchiseeList, Context context) {
        this.franchiseeList = franchiseeList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_franchise_list_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final FranchiseeList fr = franchiseeList.get(position);
        Log.e("FR", "-----------------------" + fr);
        holder.name.setText(fr.getFrName());
        holder.owner.setText(fr.getFrOwner());
        holder.mobile.setText(fr.getFrMob());
        holder.address.setText(fr.getFrAddress());

        String image = Constants.FR_IMAGE_URL + fr.getFrImage();
        try {
            Picasso.with(context)
                    .load(image)
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .into(holder.ivImage);
        } catch (Exception e) {
        }

        holder.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(fr.getFrId(), fr.getFrRouteId(), fr.getFrName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return franchiseeList.size();
    }


    public void showDialog(final int frId, final int routeId, final String frName) {

        final Dialog openDialog = new Dialog(context);
        openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        openDialog.setContentView(R.layout.custom_afe_visit_dialog);
        openDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        Window window = openDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER | Gravity.RIGHT;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        TextView tvTitle = openDialog.findViewById(R.id.tvAfeDialog_Title);
        TextView tvReport = openDialog.findViewById(R.id.tvAfeDialog_Report);
        TextView tvEnter = openDialog.findViewById(R.id.tvAfeDialog_Enter);

        tvTitle.setText("" + frName);

        tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog.dismiss();
                Intent intent = new Intent(context, AfeDateWiseReportActivity.class);
                intent.putExtra("FrId", frId);
                context.startActivity(intent);
                // AFEIVisitActivity activity = (AFEIVisitActivity) context;
                // activity.finish();
            }
        });

        tvEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog.dismiss();
                Intent intent = new Intent(context, QuestionsActivity.class);
                intent.putExtra("FrId", frId);
                intent.putExtra("RouteId", routeId);
                intent.putExtra("FrName", frName);
                context.startActivity(intent);
                //  AFEIVisitActivity activity = (AFEIVisitActivity) context;
                //  activity.finish();

            }
        });

        openDialog.show();
    }


}
